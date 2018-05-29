package com.example.wiktor.astroapp.weather;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.wiktor.astroapp.R;
import com.example.wiktor.astroapp.weather.database.models.DB_CityObject;
import com.example.wiktor.astroapp.weather.database.DatabaseManager;
import com.example.wiktor.astroapp.weather.utilities.FetchWeather;
import com.example.wiktor.astroapp.weather.utilities.MessagesDisplayer;

import java.util.List;

import io.realm.Realm;

public class WeatherActivity extends AppCompatActivity {
    /****** TO DO *******
     *
     * - zmiana jednostek - naprawić
     * - wpisywanie miasta ze spacją
     *
     *
     *
     */
    private WeatherBasicFragment weatherBasicFragment;
    private WeatherAdvancedFragment weatherAdvancedFragment;
    private WeatherForecastFragment weatherForecastFragment;

    private EditText cityInput;
    private String cityInputVal;
    private String citySearched = "";
    private Switch unitsSwitch;
    private ImageButton favoriteButton, searchButton;
    private boolean cityFavorited = false;
    private DatabaseManager databaseManager;
    private Context context;
    private boolean isDisplayingFahrenheit = true;
    private FragmentTransaction ft;

    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        try{
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }catch(Exception e){
            MessagesDisplayer.displayWithContext(context,"Couldn't hide action bar ;(");
        }
        setContentView(R.layout.activity_weather);
        addListeners();
        connectToFragments();

        databaseManager = new DatabaseManager(this);
    }
    private void addListeners() {
        cityInput = (EditText) findViewById(R.id.inputCity);
        cityInput.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if(!charSequence.toString().equals("")) {
                            if (databaseManager.cityExists(charSequence.toString())) {
                                cityFavorited = true;
                                favoriteButton.setImageResource(R.drawable.ic_heart_filled);
                            } else {
                                favoriteButton.setImageResource(R.drawable.ic_heart_empty);
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                }
        );
        searchButton = (ImageButton) findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(!cityInput.getText().toString().isEmpty()){
                cityInputVal = cityInput.getText().toString().replace(" ","%20");
                checkForWeather();
            }
        };
        });
        favoriteButton = (ImageButton)findViewById(R.id.buttonFavorite);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cityInput.getText().toString().isEmpty()) {
                    if (cityFavorited == false) {
                        databaseManager.addCity(cityInput.getText().toString());
                        favoriteButton.setImageResource(R.drawable.ic_heart_filled);
                        cityFavorited = true;
                    } else {
                        favoriteButton.setImageResource(R.drawable.ic_heart_empty);
                        if(databaseManager.cityExists(cityInput.getText().toString()))
                            databaseManager.deleteCity(cityInput.getText().toString());
                        cityFavorited = false;
                    }
                }else{
                    MessagesDisplayer.displayWithContext(context, "Input city first!");
                }
            };
        });

        favoriteButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                List<DB_CityObject> result = databaseManager.getAllCities();

                PopupMenu popup = new PopupMenu(WeatherActivity.this, favoriteButton);
                for(DB_CityObject city : result)
                    popup.getMenu().add(city.getName());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        cityInput.setText(item.getTitle());
                        checkForWeather();
                        return true;
                    }
                });
                popup.show();
                return true;
            }
        });
        unitsSwitch = (Switch) findViewById(R.id.switchTempUnit);
        unitsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeDisplayedUnit();
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        final WeatherActivity self = this;
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(checkForInternetConnection()) {
                    if(!citySearched.equals("")) {
                        new FetchWeather(self).execute(citySearched);
                        MessagesDisplayer.displayWithContext(self, "Refreshed");
                    }
                }else
                    MessagesDisplayer.displayWithContext(self, "No internet connection!");
                 mSwipeRefreshLayout.setRefreshing(false);
            }

        });
    }
    private void changeDisplayedUnit() {
        isDisplayingFahrenheit = !isDisplayingFahrenheit;
        weatherBasicFragment.changeUnit();
        weatherForecastFragment.changeUnit();
    }

    private boolean checkForInternetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void checkForWeather() {
        cityInputVal = cityInput.getText().toString().replace(" ","%20");

        if(checkForInternetConnection())
            new FetchWeather(this).execute(cityInputVal);
        else {
            MessagesDisplayer.displayWithContext(this, "No internet connection. Loading outdated data!");
            WeatherData loadedWeatherData = databaseManager.getLatestSavedWeatherForCity(cityInput.getText().toString());
            if(loadedWeatherData != null)
                useWeatherData(loadedWeatherData);
            else {
                MessagesDisplayer.displayWithContext(context,"No such location ever saved!!");
            }
        }
        citySearched = cityInput.getText().toString();
    }
    public void useWeatherData(WeatherData weatherData){
        weatherBasicFragment.setInfo(weatherData);
        weatherForecastFragment.setInfo(weatherData);
        weatherAdvancedFragment.setInfo(weatherData);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .show(weatherAdvancedFragment)
                .commit();
    }
    public DatabaseManager getDatabaseManager(){
        return this.databaseManager;
    }
    private void connectToFragments() {
        weatherBasicFragment = (WeatherBasicFragment) getSupportFragmentManager().findFragmentById(R.id.weatherBasicFragment);
        weatherAdvancedFragment = (WeatherAdvancedFragment) getSupportFragmentManager().findFragmentById(R.id.weatherAdvancedFragment);
        weatherForecastFragment = (WeatherForecastFragment) getSupportFragmentManager().findFragmentById(R.id.weatherForecastFragment);
    }


}
