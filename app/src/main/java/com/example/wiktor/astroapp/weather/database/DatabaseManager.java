package com.example.wiktor.astroapp.weather.database;

import android.content.Context;

import com.example.wiktor.astroapp.weather.DailyForecast;
import com.example.wiktor.astroapp.weather.WeatherData;
import com.example.wiktor.astroapp.weather.database.models.DB_CityObject;
//import com.example.wiktor.astroapp.weather.database.models.DB_WeatherObject;
import com.example.wiktor.astroapp.weather.database.models.DB_DailyForecast;
import com.example.wiktor.astroapp.weather.database.models.DB_WeatherObject;
import com.example.wiktor.astroapp.weather.utilities.MessagesDisplayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class DatabaseManager {
    private Realm realm;
    private Context context;

    public DatabaseManager(Context context){
        this.context = context;

        try {
            Realm.init(context);
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);
        }catch(Exception e){
            MessagesDisplayer.displayWithContext(this.context, e.toString());
        }
    }
    public void addCity(String name){
        realm.beginTransaction();
        DB_CityObject city = realm.createObject(DB_CityObject.class);
        city.setName(name);
        realm.commitTransaction();
    }
    public void addWeatherDataForOffline(WeatherData weatherData){
        realm.beginTransaction();
        DB_WeatherObject weather = realm.createObject(DB_WeatherObject.class);
        weather = getWeatherInfoFromWeatherData(weatherData);
        MessagesDisplayer.displayWithContext(context,"Saving weather for:" + weather.getCityName());
        realm.commitTransaction();
    }
    public DB_WeatherObject getWeatherInfoFromWeatherData(WeatherData weatherData){
        DB_WeatherObject DB_Weather = realm.createObject(DB_WeatherObject.class);

        DB_Weather.setCityName(weatherData.getCityName());
        DB_Weather.setCelcioDegrees(weatherData.getCelcioDegrees());
        DB_Weather.setDesc(weatherData.getDesc());
        DB_Weather.setFahrenheitDegrees(weatherData.getFahrenheitDegrees());
        DB_Weather.setLatitude(weatherData.getLatitude());
        DB_Weather.setLongitude(weatherData.getLongitude());
        DB_Weather.setPressure(weatherData.getPressure());
        DB_Weather.setTempUnit(weatherData.getTempUnit());
        DB_Weather.setTime(weatherData.getTime());
        DB_Weather.setWindSpeed(weatherData.getWindSpeed());
        DB_Weather.setWindDirection(weatherData.getWindDirection());
        DB_Weather.setHumidity(weatherData.getHumidity());

        RealmList<DB_DailyForecast> listDF = new RealmList<DB_DailyForecast>();

        for(DailyForecast df : weatherData.getForecast()){
            DB_DailyForecast DB_forecast = realm.createObject(DB_DailyForecast.class);
            DB_forecast.setDate(df.getDate());
            DB_forecast.setDesc(df.getDesc());
            DB_forecast.setTempHigh(df.getTempHigh());
            DB_forecast.setTempLow(df.getTempLow());
            DB_forecast.setTempHighC(df.getTempHighC());
            DB_forecast.setTempLowC(df.getTempLowC());
            DB_forecast.setWeekDay(df.getWeekDay());

            listDF.add(DB_forecast);
        }
        DB_Weather.setForecast(listDF);
        return DB_Weather;
    }
    public WeatherData getLatestSavedWeatherForCity(String cityName){
        DB_WeatherObject loadedDBWeatherObject = realm.where(DB_WeatherObject.class).equalTo("cityName", cityName).findFirst();
        if(loadedDBWeatherObject != null)
            return convertToWeatherData(loadedDBWeatherObject);
        else
            return null;
    }
    private WeatherData convertToWeatherData(DB_WeatherObject db_weatherObject){
        WeatherData weatherData = new WeatherData();
        weatherData.setCityName(db_weatherObject.getCityName());
        weatherData.setCelcioDegrees(db_weatherObject.getCelcioDegrees());
        weatherData.setDesc(db_weatherObject.getDesc());
        weatherData.setFahrenheitDegrees(db_weatherObject.getFahrenheitDegrees());
        weatherData.setLatitude(db_weatherObject.getLatitude());
        weatherData.setLongitude(db_weatherObject.getLongitude());
        weatherData.setPressure(db_weatherObject.getPressure());
        weatherData.setTempUnit(db_weatherObject.getTempUnit());
        weatherData.setTime(db_weatherObject.getTime());
        weatherData.setWindDirection(db_weatherObject.getWindDirection());
        weatherData.setWindSpeed(db_weatherObject.getWindSpeed());
        weatherData.setHumidity(db_weatherObject.getHumidity());
        List<DailyForecast> listDF = new ArrayList<DailyForecast>();
        for(DB_DailyForecast DB_df : db_weatherObject.getForecast()){
            DailyForecast df = new DailyForecast(DB_df.getTempHigh(),
                                                DB_df.getTempLow(),
                                                DB_df.getDesc(),
                                                DB_df.getWeekDay(),
                                                DB_df.getDate());
            listDF.add(df);
        }
        weatherData.setForecast(listDF);
        return weatherData;
    }
    public void deleteCity(String name){
        realm.beginTransaction();
        RealmResults<DB_CityObject> result = realm.where(DB_CityObject.class).equalTo("name", name).findAll();
        result.deleteAllFromRealm();
        realm.commitTransaction();
    }
    public List<DB_CityObject> getAllCities(){
        return realm.where(DB_CityObject.class).findAll();
    }
    public boolean cityExists(String name){
        RealmResults<DB_CityObject> result = realm.where(DB_CityObject.class).equalTo("name", name).findAll();
        if(result.isEmpty())
            return false;
        else
            return true;
    }
}
