package com.example.wiktor.astroapp.weather.database.models;

import io.realm.RealmObject;

public class DB_DailyForecast extends RealmObject {
    private double tempHigh, tempLow, tempHighC, tempLowC;
    private String desc, weekDay, date;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTempHigh() {
        return tempHigh;
    }

    public void setTempHigh(double tempHigh) {
        this.tempHigh = tempHigh;
    }

    public double getTempLow() {
        return tempLow;
    }

    public void setTempLow(double tempLow) {
        this.tempLow = tempLow;
    }

    public double getTempHighC() {
        return tempHighC;
    }

    public void setTempHighC(double tempHighC) {
        this.tempHighC = tempHighC;
    }

    public double getTempLowC() {
        return tempLowC;
    }

    public void setTempLowC(double tempLowC) {
        this.tempLowC = tempLowC;
    }
}
