package com.example.wiktor.astroapp.astro;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wiktor.astroapp.R;

import java.util.Date;

/**
 * Created by Wiktor on 2018-03-30.
 */

public class TimeFragment extends Fragment {

    private TextView timeText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);
        timeText = (TextView) view.findViewById(R.id.timeText);
        return view;
    }
    public void setTimeText(Date date){
        int hour = DateConverter.getHour(date),
                minutes = DateConverter.getMinutes(date),
                seconds = DateConverter.getSeconds(date);

        if (seconds % 2 == 0)
            timeText.setText(hour + ":" + minutes + " " + seconds);
        else
            timeText.setText(hour + ":" + minutes + ":" + seconds);
    }
}
