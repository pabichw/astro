package com.example.wiktor.astroapp.weather;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wiktor.astroapp.R;
import com.example.wiktor.astroapp.weather.utilities.MessagesDisplayer;

import org.w3c.dom.Text;

public class WeatherForecastFragment extends Fragment {
    private View view;
    private LinearLayout forecastContainer;
    private WeatherData weatherData;
    private boolean isDisplayedFahrenheit = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);

        addListeners();
        if(savedInstanceState != null){
            weatherData = savedInstanceState.getParcelable("WEATHER_DATA");
            setInfo(weatherData);
        }
        return view;
    }

    private void addListeners() {
        forecastContainer = (LinearLayout) view.findViewById(R.id.forecastContainer);
    }
    public void changeUnit(){
        if(weatherData != null) {
            setInfo(weatherData);
        }
    }
    public void setInfo(WeatherData weatherData) {
        this.weatherData = weatherData;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 0, 40, 0);
        forecastContainer.removeAllViews();
        for (DailyForecast df : weatherData.getForecast()) {
            try {
                LinearLayout day = new LinearLayout(view.getContext());
                day.setOrientation(LinearLayout.VERTICAL);
                day.setGravity(Gravity.CENTER_HORIZONTAL);

                TextView temp = new TextView(view.getContext()),
                        desc = new TextView(view.getContext()),
                        weekDay = new TextView(view.getContext());
                ImageView weatherIcon = new ImageView(view.getContext());

                weekDay.setText(df.getWeekDay());
                weekDay.setTextColor(Color.parseColor("#EEEEEE"));
                weekDay.setGravity(Gravity.CENTER);
                day.addView(weekDay);

                if(isDisplayedFahrenheit)
                    temp.setText(Double.toString(df.getTempHigh()).substring(0,2) + " / " + Double.toString(df.getTempLow()).substring(0,2) + " °F");
                else
                    temp.setText(Double.toString(df.getTempHighC()).substring(0,2) + " / " + Double.toString(df.getTempLowC()).substring(0,2) + " °C");

                temp.setTextColor(Color.parseColor("#FFFFFF"));
                temp.setGravity(Gravity.CENTER);
                day.addView(temp);

                switch (df.getDesc()){
                    case "Sunny":
                        weatherIcon.setImageResource(R.drawable.ic_weather_sunny);
                        break;
                    case "Mostly Sunny":
                        weatherIcon.setImageResource(R.drawable.ic_weather_sunny);
                        break;
                    case "Cloudy":
                        weatherIcon.setImageResource(R.drawable.ic_weather_cloudy);
                        break;
                    case "Partly Cloudy":
                        weatherIcon.setImageResource(R.drawable.ic_weather_cloudy);
                        break;
                    case "Scattered Thunderstorm":
                        weatherIcon.setImageResource(R.drawable.ic_weather_storm);
                        break;
                    case "Thunderstorm":
                        weatherIcon.setImageResource(R.drawable.ic_weather_storm);
                        break;
                    case "Rain":
                        weatherIcon.setImageResource(R.drawable.ic_weather_rainy);
                        break;
                    default:
                        MessagesDisplayer.displayWithContext(view.getContext(), "Not found " + df.getDesc());
                };

                day.addView(weatherIcon);

                desc.setText(df.getDesc());
                desc.setTextColor(Color.parseColor("#FFFFFF"));
                desc.setGravity(Gravity.CENTER);
                day.addView(desc);

                forecastContainer.addView(day, layoutParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        isDisplayedFahrenheit = !isDisplayedFahrenheit;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("WEATHER_DATA", weatherData);
    }
}
