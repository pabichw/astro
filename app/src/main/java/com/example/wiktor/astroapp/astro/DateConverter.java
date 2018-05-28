package com.example.wiktor.astroapp.astro;

import android.text.format.DateFormat;

import java.util.Date;

public class DateConverter {
    public static int getDay(Date date){
        return Integer.parseInt((String) DateFormat.format("dd",   date));
    }
    public static int getMonth(Date date){
        return Integer.parseInt((String) DateFormat.format("MM",   date));
    }
    public static int getYear(Date date){
        return Integer.parseInt((String) DateFormat.format("YYYY",   date));
    }
    public static int getHour(Date date){
        return Integer.parseInt((String) DateFormat.format("hh",   date));
    }
    public static int getMinutes(Date date){
        return Integer.parseInt((String) DateFormat.format("mm",   date));
    }
    public static int getSeconds(Date date){
        return Integer.parseInt((String) DateFormat.format("ss",   date));
    }
}
