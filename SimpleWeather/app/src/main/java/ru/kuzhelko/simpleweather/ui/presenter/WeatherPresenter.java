package ru.kuzhelko.simpleweather.ui.presenter;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.kuzhelko.simpleweather.network.model.City;
import ru.kuzhelko.simpleweather.network.WeatherService;
import ru.kuzhelko.simpleweather.ui.view.IWeatherView;
import ru.kuzhelko.simpleweather.utils.Constants;

@InjectViewState
public class WeatherPresenter extends MvpPresenter<IWeatherView> {

    WeatherService weatherService;

    public WeatherPresenter() {
    }

    public void setUpWeatherService(WeatherService service) {
        weatherService = service;
    }

    public void loadWeather(String cityName) {

        getViewState().showLoading();
        Observable.fromArray(cityName)
                .delay(300, TimeUnit.MILLISECONDS)
                .filter(text -> !text.isEmpty())
                .switchMap(query -> weatherService.getWeather(query, Constants.API_KEY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(city -> {
                            Log.i("TEST", "CITY " + city + " LOADED FROM NETWORK");
                            getViewState().hideLoading();
                            getViewState().setLoadedCityFromNetwork(city);
                            getViewState().showWeather(city);
                        },
                        throwable -> {
                            Log.i("TEST", "ERROR");
                            getViewState().hideLoading();
                            getViewState().showError();
                        }
                );
    }

    public void setListenerForSearshView(SearchView searchView, Bundle previousState) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                loadWeather(text);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                Log.i("TEST", "INPUT SUMBOL = " + text);
                getPreviousIfExist(previousState, text);
                return true;
            }
        });
    }

    public void getPreviousIfExist(Bundle previousState, String text) {
        if (previousState == null) {
            loadWeather(text);
        } else {
            City loadedCity = (City) previousState.getSerializable(Constants.WEATHER_KEY);
            if (!loadedCity.getName().toLowerCase().equals(text.toLowerCase())) {
                loadWeather(text);
            }
        }
    }
}