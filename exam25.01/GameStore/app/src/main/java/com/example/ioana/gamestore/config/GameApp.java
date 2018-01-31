package com.example.ioana.gamestore.config;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.util.Log;

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

        clientDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "clientDatabase")
                .fallbackToDestructiveMigration() /*these are necessary*/
                .allowMainThreadQueries()
                .build();
        employeeDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "employeeDatabase")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        rentDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "rentDatabase")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        buyDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "buyDatabase")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }
}
