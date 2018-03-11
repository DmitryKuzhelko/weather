package ru.kuzhelko.simpleweather.ui.view;

import com.arellomobile.mvp.MvpView;

import ru.kuzhelko.simpleweather.network.model.City;

public interface IWeatherView extends MvpView {

    void showWeather(City city);

    void showLoading();

    void hideLoading();

    void showError();

    void setLoadedCityFromNetwork(City city);
}