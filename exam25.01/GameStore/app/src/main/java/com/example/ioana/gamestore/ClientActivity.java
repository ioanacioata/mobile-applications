package com.example.ioana.gamestore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.ioana.gamestore.config.Manager;

import timber.log.Timber;

public class ClientActivity extends AppCompatActivity {

    private static final String TAG = ClientActivity.class.getName();
    private Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);


        Timber.i("Loading data with timber");
        Log.i(TAG, "Loading data with log");
        manager = new Manager();
        ProgressBar progressBar = findViewById(R.id.progressBarClientList);
        manager.loadEvents(progressBar);

    }
}
