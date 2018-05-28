package com.example.wiktor.astroapp.weather.utilities;

import android.content.Context;
import android.widget.Toast;

public class MessagesDisplayer {

    public static void displayWithContext(Context context, String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
