package com.example.wiktor.astroapp.weather.utilities;

public class TempConverter {
    public static double fahrenheitToCelcio(double fahrVal){
        return Math.round((fahrVal - 32) * 5/9);
    }
}
