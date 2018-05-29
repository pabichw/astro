package com.example.wiktor.astroapp.weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wiktor.astroapp.R;
import com.example.wiktor.astroapp.weather.utilities.MessagesDisplayer;

import org.w3c.dom.Text;

public class WeatherBasicFragment extends Fragment {
    private View view;
    private TextView  tempUnitLabel, cityNameLabel, timeLabel, pressureLabel,
                    descLabel, coordsLabel;
    private ImageView weatherIcon;
    private WeatherData weatherData;
    private boolean isDisplayingFahrenheit = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather_basic, container, false);
        setListeners();

        if(savedInstanceState != null){
            weatherData = savedInstanceState.getParcelable("WEATHER_DATA");
            setInfo(weatherData);
        }

        return view;
    }

    private void setListeners() {
        tempUnitLabel = (TextView) view.findViewById(R.id.tempUnitLabel);
        cityNameLabel = (TextView) view.findViewById(R.id.cityNameLabel);
        weatherIcon = (ImageView) view.findViewById(R.id.weatherIcon);
        timeLabel = (TextView) view.findViewById(R.id.timeLabel);
        pressureLabel = (TextView) view.findViewById(R.id.pressureLabel);
        descLabel = (TextView) view.findViewById(R.id.descLabel);
        coordsLabel = (TextView) view.findViewById(R.id.coordsLabel);
    }
    public void changeUnit(String changeToUnit){
        if(weatherData != null) {
            if(changeToUnit.equals("celcio"))
                isDisplayingFahrenheit = false;
            else
                isDisplayingFahrenheit = true;
            setInfo(weatherData);
        }
    }
    public void setInfo(WeatherData weatherData){
        this.weatherData = weatherData;
        try {

            cityNameLabel.setText(weatherData.getCityName());
            if(isDisplayingFahrenheit)
                tempUnitLabel.setText(weatherData.getFahrenheitDegrees()+ " °F");
            else
                tempUnitLabel.setText(weatherData.getCelcioDegrees()+ " °C");

            setWeatherIcon(weatherData);
            timeLabel.setText(weatherData.getTime());
            pressureLabel.setText(weatherData.getPressure() + " hPa");
            descLabel.setText(weatherData.getDesc());
            coordsLabel.setText(weatherData.getCoordLat() + " " + weatherData.getCoordLong());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void setWeatherIcon(WeatherData weatherData) {
        switch (weatherData.getDesc()){
            case "Sunny":
                weatherIcon.setImageResource(R.drawable.ic_weather_sunny);
                break;
            case "Mostly Sunny":
                weatherIcon.setImageResource(R.drawable.ic_weather_sunny);
                break;
            case "Cloudy":
                weatherIcon.setImageResource(R.drawable.ic_weather_cloudy);
                break;
            case "Clear":
                weatherIcon.setImageResource(R.drawable.ic_weather_clear);
                break;
            case "Mostly Cloudy":
                weatherIcon.setImageResource(R.drawable.ic_weather_cloudy);
                break;
            case "Partly Cloudy":
                weatherIcon.setImageResource(R.drawable.ic_weather_cloudy);
                break;
            case "Scattered Thunderstorms":
                weatherIcon.setImageResource(R.drawable.ic_weather_storm);
                break;
            case "Thunderstorms":
                weatherIcon.setImageResource(R.drawable.ic_weather_storm);
                break;
            case "Rain":
                weatherIcon.setImageResource(R.drawable.ic_weather_rainy);
                break;
            case "Scattered Showers":
                weatherIcon.setImageResource(R.drawable.ic_weather_rainy);
                break;
            default:
                MessagesDisplayer.displayWithContext(view.getContext(), "Not found " + weatherData.getDesc());
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("WEATHER_DATA", weatherData);
    }
}
