package com.example.wiktor.astroapp.weather.database.models;

import com.example.wiktor.astroapp.weather.DailyForecast;
import com.example.wiktor.astroapp.weather.WeatherData;

import java.util.List;

import io.realm.RealmObject;

public class DB_CityObject extends RealmObject {

    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
