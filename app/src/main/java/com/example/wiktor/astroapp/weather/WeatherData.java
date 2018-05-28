package com.example.wiktor.astroapp.weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.wiktor.astroapp.weather.utilities.TempConverter;

import java.util.ArrayList;
import java.util.List;

public class WeatherData implements Parcelable{
    private String fahrenheitDegrees, celcioDegrees, tempUnit, time, cityName, pressure, desc, latitude, longitude, humidity, visibility, windSpeed, windDirection;
    private List<DailyForecast> forecast;


    public WeatherData(){}
    public void setFahrenheitDegrees(String val){
        fahrenheitDegrees = val;
        celcioDegrees = Double.toString(TempConverter.fahrenheitToCelcio(Double.parseDouble(fahrenheitDegrees))).substring(0,2);
    }
    public String getFahrenheitDegrees(){return fahrenheitDegrees;}

    public String getTempUnit() {
        return tempUnit;
    }

    public void setTempUnit(String tempUnit) {
        this.tempUnit = tempUnit;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCelcioDegrees() {
        return celcioDegrees;
    }

    public void setCelcioDegrees(String celcioDegrees) {
        this.celcioDegrees = celcioDegrees;
    }

    public List<DailyForecast> getForecast() {
        return forecast;
    }

    public void setForecast(List<DailyForecast> forecast) {
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

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    /* PARCELABLE METHODS */
    public int describeContents() {
        return 0;
    }
    /** save object in parcel */
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(fahrenheitDegrees);
        out.writeString(celcioDegrees);
        out.writeString(tempUnit);
        out.writeString(time);
        out.writeString(cityName);
        out.writeString(pressure);
        out.writeString(desc);
        out.writeString(latitude);
        out.writeString(longitude);
        out.writeString(humidity);
        out.writeString(visibility);
        out.writeString(windSpeed);
        out.writeString(windDirection);
        out.writeList(forecast);

    }

    public static final Parcelable.Creator<WeatherData> CREATOR = new Parcelable.Creator<WeatherData>() {
        public WeatherData createFromParcel(Parcel in) {
            return new WeatherData(in);
        }

        public WeatherData[] newArray(int size) {
            return new WeatherData[size];
        }
    };

    /** recreate object from parcel */
    private WeatherData(Parcel in) {
        fahrenheitDegrees = in.readString();
        celcioDegrees = in.readString();
        tempUnit = in.readString();
        time = in.readString();
        cityName = in.readString();
        pressure = in.readString();
        desc = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        humidity = in.readString();
        visibility = in.readString();
        windSpeed = in.readString();
        windDirection = in.readString();
        in.readList(forecast, null);
    }
}
