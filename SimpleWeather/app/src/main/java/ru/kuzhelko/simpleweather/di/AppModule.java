package ru.kuzhelko.simpleweather.di;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.kuzhelko.simpleweather.network.WeatherService;
import ru.kuzhelko.simpleweather.utils.Constants;

@Module
public class AppModule {

    @Singleton
    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    WeatherService provideWeatherService(Retrofit retrofit) {
        return retrofit.create(WeatherService.class);
    }
}
