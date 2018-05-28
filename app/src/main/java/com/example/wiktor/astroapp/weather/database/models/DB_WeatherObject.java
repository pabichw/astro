package com.example.wiktor.astroapp.weather.database.models;

import com.example.wiktor.astroapp.weather.DailyForecast;
import com.example.wiktor.astroapp.weather.WeatherData;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class DB_WeatherObject extends RealmObject {

    private String fahrenheitDegrees, celcioDegrees, tempUnit, time, cityName, pressure, desc,
                    latitude, longitude, windSpeed, windDirection, humidity, visibility;
    private RealmList<DB_DailyForecast> forecast;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTempUnit() {
        return tempUnit;
    }

    public void setTempUnit(String tempUnit) {
        this.tempUnit = tempUnit;
    }

    public String getCelcioDegrees() {
        return celcioDegrees;
    }

    public void setCelcioDegrees(String celcioDegrees) {
        this.celcioDegrees = celcioDegrees;
    }

    public String getFahrenheitDegrees() {
        return fahrenheitDegrees;
    }

    public void setFahrenheitDegrees(String fahrenheitDegrees) {
        this.fahrenheitDegrees = fahrenheitDegrees;
    }



    public RealmList<DB_DailyForecast> getForecast() {
        return forecast;
    }

    public void setForecast(RealmList<DB_DailyForecast> forecast) {
        this.forecast = forecast;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}
