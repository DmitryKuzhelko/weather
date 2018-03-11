package ru.kuzhelko.simpleweather.ui.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.kuzhelko.simpleweather.R;
import ru.kuzhelko.simpleweather.network.model.City;
import ru.kuzhelko.simpleweather.ui.presenter.WeatherPresenter;
import ru.kuzhelko.simpleweather.utils.App;
import ru.kuzhelko.simpleweather.utils.Constants;

public class WeatherActivity extends MvpAppCompatActivity implements IWeatherView {

    @InjectPresenter
    WeatherPresenter presenter;

    @BindView(R.id.searchView)
    SearchView searchView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.city_title)
    TextView mCityTitle;

    @BindView(R.id.cardView)
    View cardView;

    @BindView(R.id.weather_main)
    TextView mWeatherMain;

    @BindView(R.id.temperature)
    TextView mTemperature;

    @BindView(R.id.pressure)
    TextView mPressure;

    @BindView(R.id.humidity)
    TextView mHumidity;

    @BindView(R.id.wind_speed)
    TextView mWindSpeed;

    @BindView(R.id.error_layout)
    TextView mErrorLayout;

    @Nullable
    private City loadedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        setUpWeatherService();

        setUpSearchView(searchView, savedInstanceState);

        checkPreviousState(savedInstanceState);
    }

    private void setUpWeatherService() {
        presenter.setUpWeatherService(App.getAppComponent().getWeatherService());
    }

    private void checkPreviousState(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.WEATHER_KEY)) {
            loadedCity = (City) savedInstanceState.getSerializable(Constants.WEATHER_KEY);
            Log.i("TEST", "CITY " + loadedCity + " RELOADED FROM BUNDLE");
            showWeather(loadedCity);
        }
    }

    private void setUpSearchView(SearchView searchView, Bundle previousState) {
        presenter.setListenerForSearshView(searchView, previousState);
    }

    public void setLoadedCityFromNetwork(City city) {
        this.loadedCity = city;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (loadedCity != null) {
            outState.putSerializable(Constants.WEATHER_KEY, loadedCity);
        }
    }

    public void showWeather(City city) {
        mErrorLayout.setVisibility(View.GONE);
        cardView.setVisibility(View.VISIBLE);

        mCityTitle.setText(city.getName());
        mWeatherMain.setText(city.getWeather().getMain());
        mTemperature.setText(getString(R.string.f_temperature, city.getMain().getTemp()));
        mPressure.setText(getString(R.string.f_pressure, city.getMain().getPressure()));
        mHumidity.setText(getString(R.string.f_humidity, city.getMain().getHumidity()));
        mWindSpeed.setText(getString(R.string.f_wind_speed, city.getWind().getSpeed()));
    }


    @Override
    public void showLoading() {
        cardView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        cardView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        cardView.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.VISIBLE);
    }
}