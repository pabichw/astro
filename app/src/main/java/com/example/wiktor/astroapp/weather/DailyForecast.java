package com.example.wiktor.astroapp.weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.wiktor.astroapp.weather.utilities.TempConverter;

public class DailyForecast implements Parcelable{
    private double tempHigh, tempLow, tempHighC, tempLowC;
    private String desc, weekDay, date;

    public DailyForecast(double tempHigh, double tempLow, String desc, String weekDay, String date){
        this.tempHigh = tempHigh;
        this.tempLow = tempLow;
        this.tempHighC = TempConverter.fahrenheitToCelcio(this.tempHigh);
        this.tempLowC = TempConverter.fahrenheitToCelcio(this.tempLow);
        this.desc = desc;
        this.weekDay = weekDay;
        this.date = date;
    }
    public double getTempHigh() {
        return tempHigh;
    }
    public void setTempHigh(int tempHigh) {
        this.tempHigh = tempHigh;
    }

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

    public double getTempLow() {
        return tempLow;
    }

    public void setTempLow(int tempLow) {
        this.tempLow = tempLow;
    }

    public double getTempHighC() {
        return tempHighC;
    }

    public void setTempHighC(int tempHighC) {
        this.tempHighC = tempHighC;
    }

    public double getTempLowC() {
        return tempLowC;
    }

    public void setTempLowC(int tempLowC) {
        this.tempLowC = tempLowC;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeDouble(tempHigh);
        out.writeDouble(tempLow);
        out.writeDouble(tempHighC);
        out.writeDouble(tempLowC);
        out.writeString(desc);
        out.writeString(weekDay);
        out.writeString(date);
    }

    public static final Parcelable.Creator<DailyForecast> CREATOR
            = new Parcelable.Creator<DailyForecast>() {
        public DailyForecast createFromParcel(Parcel in) {
            return new DailyForecast(in);
        }

        public DailyForecast[] newArray(int size) {
            return new DailyForecast[size];
        }
    };

    /** recreate object from parcel */
    private DailyForecast(Parcel in) {
        tempHigh = in.readDouble();
        tempLow = in.readDouble();
        tempHighC = in.readDouble();
        tempLowC = in.readDouble();
        desc = in.readString();
        weekDay = in.readString();
        date = in.readString();
    }
}
