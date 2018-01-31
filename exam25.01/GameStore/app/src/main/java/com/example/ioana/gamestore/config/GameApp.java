package com.example.ioana.gamestore.config;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.util.Log;

import timber.log.Timber;

/**
 * Created by Ioana on 31/01/2018.
 */

public class GameApp extends Application {
    public AppDatabase clientDatabase;
    public AppDatabase employeeDatabase;
    public AppDatabase rentDatabase;
    public AppDatabase buyDatabase;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Application ", "Creating the database");
        Timber.plant(new Timber.DebugTree());

        clientDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "clientDatabase")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        employeeDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "employeeDatabase").build();
        rentDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "rentDatabase").build();
        buyDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "buyDatabase").build();
    }
}
