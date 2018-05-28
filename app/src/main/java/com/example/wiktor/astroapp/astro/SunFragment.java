package com.example.wiktor.astroapp.astro;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Fragment;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;
import com.example.wiktor.astroapp.R;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Wiktor on 2018-03-29.
 */

public class SunFragment extends Fragment {
    private View view;
    private TextView sunriseTime, sunsetTime, azimuthRiseTime, azimuthSetTime, twilightEvening, twilightMorning;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sun, container, false);

        sunriseTime = (TextView) view.findViewById(R.id.sunriseTime);
        sunsetTime = (TextView) view.findViewById(R.id.sunsetTime);
        azimuthRiseTime = (TextView) view.findViewById(R.id.azimuthRiseTime);
        azimuthSetTime = (TextView) view.findViewById(R.id.azimuthSetTime);
        twilightEvening = (TextView) view.findViewById(R.id.twilightEvening);
        twilightMorning = (TextView) view.findViewById(R.id.twilightMorning);
        return view;
    }
    public void setInfo(AstroCalculator ac){
        try {
            sunriseTime.setText(
                    Integer.toString(ac.getSunInfo().getSunrise().getHour()) + ":" +
                            Integer.toString(ac.getSunInfo().getSunrise().getMinute())
            );
            sunsetTime.setText(
                    Integer.toString(ac.getSunInfo().getSunset().getHour()) + ":" +
                            Integer.toString(ac.getSunInfo().getSunset().getMinute())
            );
            double roundedAzimothRiseTime = round(ac.getSunInfo().getAzimuthRise(), 2);
            azimuthRiseTime.setText(
                    Double.toString(roundedAzimothRiseTime)
            );
            double roundedAzimothSetTime = round(ac.getSunInfo().getAzimuthSet(), 2);
            azimuthSetTime.setText(
                    Double.toString(roundedAzimothSetTime)
            );
            AstroDateTime adtTwilightEvening = ac.getSunInfo().getTwilightEvening();
            twilightEvening.setText(
                    Integer.toString(adtTwilightEvening.getHour()) + ":" +
                            Integer.toString(adtTwilightEvening.getMinute())
            );
            AstroDateTime adtTwilightMorning = ac.getSunInfo().getTwilightMorning();
            twilightMorning.setText(
                    Integer.toString(adtTwilightMorning.getHour()) + ":" +
                            Integer.toString(adtTwilightMorning.getMinute())
            );
        }catch(Exception e){
            //some exception caught but thats fiiiine
            //https://www.geek.com/wp-content/uploads/2016/08/this-is-fine-meme-625x350.jpg
        }
    }
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
