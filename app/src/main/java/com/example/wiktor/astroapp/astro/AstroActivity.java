package com.example.wiktor.astroapp.astro;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;
import com.example.wiktor.astroapp.R;

import static java.lang.Math.toIntExact;
import static java.util.Calendar.getInstance;

public class AstroActivity extends AppCompatActivity {

    private EditText longiInput, latiInput, freqInput;
    private TextView timeText, longAndLatiText;
    private SunFragment sunFragment;
    private MoonFragment moonFragment;
    private TimeFragment timeFragment;
    private Button changeButton;
    private Date date;
    private double longitude, latitude;
    private boolean isTablet;
    private long astroRefreshInterval;
    private Thread astroUpdateThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }catch(Exception e){
            displayToastMessage("Couldn't hide action bar ;(");
        }

        setContentView(R.layout.activity_astro);
        try {
            addElementsListeners();
        }catch(Exception e){
            displayToastMessage(e.getMessage());
        }
        connectToFragments();

        isTablet = getResources().getBoolean(R.bool.isTablet);
        if(!isTablet) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.hide(sunFragment).commit();
        }

        runTimeUpdateThread();
    }
    private void connectToFragments(){
        sunFragment = (SunFragment)getFragmentManager().findFragmentById(R.id.sunFragment);
        moonFragment = (MoonFragment)getFragmentManager().findFragmentById(R.id.moonFragment);
        timeFragment = (TimeFragment)getFragmentManager().findFragmentById(R.id.timeFragment);
    }
    private boolean validCoords(){
        if((longitude >= -180 && longitude <= 180) && (latitude >= -90 && latitude <= 90))
            return true;
        else
            return false;
    }
    private void addElementsListeners() {
        longiInput = (EditText) findViewById(R.id.longiInput);
        longiInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                int textLength = longiInput.getText().toString().length();
                int dotCount = count - longiInput.getText().toString().replace(".", "").length();
                int minusCount = textLength - longiInput.getText().toString().replace("-", "").length();

                if((count > 0 && dotCount <= 1 && minusCount<=1) && !longiInput.getText().toString().equals(".") && !longiInput.getText().toString().equals("-"))
                    longitude = Double.parseDouble(longiInput.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(longiInput.getText().length() > 0 && !longiInput.getText().toString().equals(".") && !longiInput.getText().toString().equals("-")) {
                    if(validCoords()) {
                        setSunInfo();
                        setMoonInfo();
                        longAndLatiText.setText("Longitude: " + Double.toString(longitude) + " Latitude: " + Double.toString(latitude));
                    }else{
                        displayToastMessage("Invalid coords!");
                    }
                }
            }
        });
        latiInput = (EditText) findViewById(R.id.latiInput);
        latiInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                int textLength = latiInput.getText().toString().length();
                int dotCount = count - latiInput.getText().toString().replace(".", "").length();
                int minusCount = textLength - latiInput.getText().toString().replace("-", "").length();

                if((count > 0 && dotCount <= 1 && minusCount<=1) && !latiInput.getText().toString().equals(".") && !latiInput.getText().toString().equals("-"))
                    latitude = Double.parseDouble(latiInput.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(latiInput.getText().length() > 0 && !latiInput.getText().toString().equals(".") && !latiInput.getText().toString().equals("-")) {
                    if(validCoords()) {
                        setSunInfo();
                        setMoonInfo();
                        longAndLatiText.setText("Longitude: " + Double.toString(longitude) + " Latitude: " + Double.toString(latitude));
                    }else{
                        displayToastMessage("Invalid coords!");
                    }
                }
            }
        });
        freqInput = (EditText) findViewById(R.id.freqInput);
        freqInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    astroRefreshInterval = Integer.parseInt(editable.toString()) * 60 * 1000;
                    runAstroUpdateThread();
                }catch(Exception e){
                    displayToastMessage("Invalid input as frequency");
                }
            }
        });

        timeText = (TextView) findViewById(R.id.timeText);
        longAndLatiText = (TextView) findViewById(R.id.longAndLatiText);

        changeButton = (Button) findViewById(R.id.buttonChange);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDisplayedFragment();
            }
        });
    }


    private void changeDisplayedFragment() {
        FragmentManager fm = getFragmentManager();
        if(sunFragment.isHidden()) {

            fm.beginTransaction()
                    .show(sunFragment)
                    .commit();
            fm.beginTransaction()
                    .hide(moonFragment)
                    .commit();
        }else{
            fm.beginTransaction()
                    .show(moonFragment)
                    .commit();
            fm.beginTransaction()
                    .hide(sunFragment)
                    .commit();
        }
    }

    private void runTimeUpdateThread() {
        Thread timeUpdateThread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                date = getInstance().getTime();
                                timeFragment.setTimeText(date);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    displayToastMessage("InterruptedException catched");
                }
            }
        };
        timeUpdateThread.start();
    }
    private void runAstroUpdateThread() {
         astroUpdateThread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(astroRefreshInterval);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setMoonInfo();
                                setSunInfo();
                                displayToastMessage("Astro Data updated");
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    displayToastMessage("InterruptedException catched");
                }
            }
        };
        astroUpdateThread.start();
    }

    private void setSunInfo() {
        Calendar calendar = Calendar.getInstance();
        Calendar mCalendar = new GregorianCalendar();
        TimeZone mTimeZone = mCalendar.getTimeZone();
        int mGMTOffset = mTimeZone.getRawOffset();

        AstroCalculator astroCalculator = new AstroCalculator(new AstroDateTime(
                calendar.get(calendar.YEAR), calendar.get(calendar.MONTH) + 1, calendar.get(calendar.DAY_OF_MONTH), calendar.get(calendar.HOUR), calendar.get(calendar.MINUTE),
                calendar.get(calendar.SECOND), (int)TimeUnit.HOURS.convert(mGMTOffset, TimeUnit.MILLISECONDS),TimeZone.getDefault().inDaylightTime( new Date() )
        ), new AstroCalculator.Location(latitude, longitude));
        try {
            sunFragment.setInfo(astroCalculator);
        }catch(Exception e){
            displayToastMessage(e.getMessage());
        }
    }
    private void setMoonInfo(){
        Calendar calendar = Calendar.getInstance();
        Calendar mCalendar = new GregorianCalendar();
        TimeZone mTimeZone = mCalendar.getTimeZone();
        int mGMTOffset = mTimeZone.getRawOffset();

        AstroCalculator astroCalculator = new AstroCalculator(new AstroDateTime(
                calendar.get(calendar.YEAR), calendar.get(calendar.MONTH) + 1, calendar.get(calendar.DAY_OF_MONTH), calendar.get(calendar.HOUR), calendar.get(calendar.MINUTE),
                calendar.get(calendar.SECOND), (int)TimeUnit.HOURS.convert(mGMTOffset, TimeUnit.MILLISECONDS), true
        ), new AstroCalculator.Location(latitude, longitude));

        try {
            moonFragment.setInfo(astroCalculator);
        }catch(Exception e){
            displayToastMessage(e.getMessage());
        }
    }

    public void displayToastMessage(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}