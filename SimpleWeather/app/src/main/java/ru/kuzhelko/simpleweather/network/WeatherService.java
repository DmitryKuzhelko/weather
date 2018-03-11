package ru.kuzhelko.simpleweather.network;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.kuzhelko.simpleweather.network.model.City;

public interface WeatherService {
    @GET("data/2.5/weather?units=metric")
    Observable<City> getWeather(@NonNull @Query("q") String city, @Query("APPID") String apiKey);
}