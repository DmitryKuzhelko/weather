package ru.kuzhelko.simpleweather.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.kuzhelko.simpleweather.network.WeatherService;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    WeatherService getWeatherService();
}
