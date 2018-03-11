package ru.kuzhelko.simpleweather.utils;

import android.app.Application;

import ru.kuzhelko.simpleweather.di.AppComponent;
import ru.kuzhelko.simpleweather.di.DaggerAppComponent;

public class App extends Application {

    public static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initComponent();
    }

    private void initComponent() {
        appComponent = DaggerAppComponent.create();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}