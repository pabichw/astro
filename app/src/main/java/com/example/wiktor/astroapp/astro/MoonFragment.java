package com.example.wiktor.astroapp.astro;

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

public class MoonFragment extends Fragment {
    private View view;
    private TextView moonriseTime, moonsetTime, nextFullMoon, nextNewMoon, moonIllumination, moonSynodicDay;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_moon, container, false);

        moonriseTime = (TextView) view.findViewById(R.id.moonriseTime);
        moonsetTime = (TextView) view.findViewById(R.id.moonsetTime);
        nextFullMoon = (TextView) view.findViewById(R.id.nextFullMoon);
        nextNewMoon = (TextView) view.findViewById(R.id.nextNewMoon);
        moonIllumination = (TextView) view.findViewById(R.id.moonIllumination);
        moonSynodicDay = (TextView) view.findViewById(R.id.moonSynodicDay);
        return view;
    }

    public void setInfo(AstroCalculator ac){
        try {
            moonriseTime.setText(
                    Integer.toString(ac.getMoonInfo().getMoonrise().getHour()) + ":" +
                            Integer.toString(ac.getMoonInfo().getMoonrise().getMinute())
            );

            moonsetTime.setText(
                    Integer.toString(ac.getMoonInfo().getMoonset().getHour())  + ":" +
                            Integer.toString(ac.getMoonInfo().getMoonset().getMinute())
            );
            AstroDateTime adtNextFullMoon = ac.getMoonInfo().getNextFullMoon();
            nextFullMoon.setText(
                    Integer.toString(adtNextFullMoon.getDay()) + "/" +
                            Integer.toString(adtNextFullMoon.getMonth() + 1 )+ " " +
                            Integer.toString(adtNextFullMoon.getHour()) + ":" +
                            Integer.toString(adtNextFullMoon.getMinute())
            );
            AstroDateTime adtNextNewMoon = ac.getMoonInfo().getNextNewMoon();
            nextNewMoon.setText(
                    Integer.toString(adtNextNewMoon.getDay()) + "/" +
                    Integer.toString(adtNextNewMoon.getMonth() + 1 )+ " " +
                    Integer.toString(adtNextNewMoon.getHour()) + ":" +
                    Integer.toString(adtNextNewMoon.getMinute())
            );
            double val = round(ac.getMoonInfo().getIllumination(), 2);
            moonIllumination.setText(Double.toString(val) + "%");

            moonSynodicDay.setText(
                    Double.toString(round(ac.getMoonInfo().getAge(), 0))
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
