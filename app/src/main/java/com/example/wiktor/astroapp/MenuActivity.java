package com.example.wiktor.astroapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.wiktor.astroapp.astro.AstroActivity;
import com.example.wiktor.astroapp.weather.WeatherActivity;

public class MenuActivity extends AppCompatActivity {

    private Button buttonAstro, buttonWeather, buttonExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu);

        addListeners();
    }

    private void addListeners(){
        buttonAstro = (Button) findViewById(R.id.buttonAstro);
        buttonAstro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent astroIntent = new Intent(MenuActivity.this, AstroActivity.class);
                startActivity(astroIntent);
            }
        });
        buttonWeather = (Button) findViewById(R.id.buttonWeather);
        buttonWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent weatherIntent = new Intent(MenuActivity.this, WeatherActivity.class);
                startActivity(weatherIntent);
            }
        });
        buttonExit = (Button) findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);            }
        });
    }
}
