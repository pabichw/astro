package com.example.wiktor.astroapp.weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wiktor.astroapp.R;

public class WeatherAdvancedFragment extends Fragment {
    private View view;
    private WeatherData weatherData;
    private TextView windLabel, humidityLabel, visibilityLabel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather_advanced, container, false);

        addListeners();

        if(savedInstanceState != null){
            weatherData = savedInstanceState.getParcelable("WEATHER_DATA");
            if(weatherData != null)
                setInfo(weatherData);
        }
        return view;
    }

    private void addListeners() {
        windLabel = (TextView) view.findViewById(R.id.windLabel);
        humidityLabel = (TextView) view.findViewById(R.id.humidityLabel);
        visibilityLabel = (TextView) view.findViewById(R.id.visibilityLabel);
    }

    public void setInfo(WeatherData weatherData){
        this.weatherData = weatherData;
        windLabel.setText("Wind:\t" + weatherData.getWindDirection() + " | " + weatherData.getWindSpeed() + "°");
        humidityLabel.setText("Humidity:\t" + weatherData.getHumidity() + "%");
        visibilityLabel.setText("Visibility:\t" + weatherData.getVisibility() + "%");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("WEATHER_DATA", weatherData);
    }
}
