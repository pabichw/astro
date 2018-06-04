package com.example.wiktor.astroapp.weather.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.example.wiktor.astroapp.weather.DailyForecast;
import com.example.wiktor.astroapp.weather.WeatherActivity;
import com.example.wiktor.astroapp.weather.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchWeather extends AsyncTask<String, Void, WeatherData>{
    private WeatherActivity main;
    private String cityName;
    private Context context;

    public FetchWeather(WeatherActivity main){
        this.main = main;
    }
    public FetchWeather(WeatherActivity main, Context context){
        this.main = main;
        this.context = context;
    }
    protected WeatherData doInBackground(String... cityName){
        HttpURLConnection urlConnection = null;
        StringBuilder result = new StringBuilder();
        this.cityName = cityName[0];
        try {
            String rootURL = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"+ this.cityName +"%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
            URL url = new URL(rootURL);

            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace(); //netowrkOnMainThreadException
        } finally {
            urlConnection.disconnect();
        }

        WeatherData weatherData = parseJSON(result);

        return weatherData;
    }

    private WeatherData parseJSON(StringBuilder result) {
        final WeatherData weatherData = new WeatherData();
        try {
            JSONObject jObject = new JSONObject(result.toString());

            if(jObject.getJSONObject("query").getJSONObject("results").equals(null))
                return null;

            weatherData.setFahrenheitDegrees(jObject.getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("item")
                    .getJSONObject("condition")
                    .getString("temp"));
            weatherData.setTempUnit(jObject.getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("units")
                    .getString("temperature"));
            weatherData.setCityName(jObject.getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("location")
                    .getString("city"));
            weatherData.setTime(jObject.getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("item")
                    .getJSONObject("condition")
                    .getString("date"));
            weatherData.setDesc(jObject.getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("item")
                    .getJSONObject("condition")
                    .getString("text"));
            weatherData.setPressure(jObject.getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("atmosphere")
                    .getString("pressure"));
            weatherData.setCoordLat(jObject.getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("item")
                    .getString("lat"));
            weatherData.setCoordLong(jObject.getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("item")
                    .getString("long"));
            JSONObject forecastObj = jObject.getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("item");

            JSONArray forecastArray = forecastObj.getJSONArray("forecast");
            List<DailyForecast> forecastList = new ArrayList<DailyForecast>();

            for(int i = 0; i < forecastArray.length(); i++){
                JSONObject object = forecastArray.getJSONObject(i);
                String highTemp = object.getString("high"),
                        lowTemp = object.getString("low"),
                        desc = object.getString("text"),
                        weekDay = object.getString("day"),
                        date = object.getString("date");

                DailyForecast day = new DailyForecast(Integer.parseInt(highTemp), Integer.parseInt(lowTemp), desc, weekDay, date);
                forecastList.add(day);
            }
            weatherData.setForecast(forecastList);

            weatherData.setHumidity(jObject.getJSONObject("query")
                                            .getJSONObject("results")
                                            .getJSONObject("channel")
                                            .getJSONObject("atmosphere")
                                            .getString("humidity"));
            weatherData.setVisibility(jObject.getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("atmosphere")
                    .getString("visibility"));
            weatherData.setWindSpeed(jObject.getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("wind")
                    .getString("speed"));
            weatherData.setWindDirection(jObject.getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("wind")
                    .getString("direction"));

            //if nazwa miasta odebranego to nie jest rowna wpisanej to wyswietl komunikat
        } catch (JSONException e) {
            e.printStackTrace();
            //MessagesDisplayer.displayWithContext(this.context,"No such city found !!!");
            Handler handler =  new Handler(context.getMainLooper());
            handler.post( new Runnable(){
                public void run(){
                    Toast.makeText(context, "No such or similar location found !!! :(",Toast.LENGTH_LONG).show();
                }
            });
            return null;
        }

        return weatherData;
    }

    @Override
    protected void onPostExecute(WeatherData weatherData) {
        try {
            if(weatherData != null) {
                main.useWeatherData(weatherData);
                main.getDatabaseManager().addWeatherDataForOffline(weatherData);
            }else{

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
