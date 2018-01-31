package com.example.ioana.gamestore.ui;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ioana.gamestore.R;
import com.example.ioana.gamestore.config.GameApp;
import com.example.ioana.gamestore.config.Manager;
import com.example.ioana.gamestore.config.MyCallback;
import com.example.ioana.gamestore.domain.Game;
import com.example.ioana.gamestore.ui.adapter.MyAdapter;

import java.util.List;

import timber.log.Timber;

public class ClientActivity extends AppCompatActivity implements MyCallback, View.OnClickListener {

    private static final String TAG = ClientActivity.class.getName();
    TextView text;
    ProgressBar progressBar;
    private Manager manager;

    private MyAdapter adapter;
    private View recycleViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        Timber.i("Loading data with timber");
        manager = new Manager(getApplication());

        progressBar = findViewById(R.id.progressBarClientList);
        progressBar.setVisibility(View.GONE);


        text = findViewById(R.id.currentListText);
        text.setText("Nothing to display ... click on one of the buttons ");

        findViewById(R.id.displayAllClient).setOnClickListener(this);
        findViewById(R.id.displayBought).setOnClickListener(this);
        findViewById(R.id.displayRented).setOnClickListener(this);


        recycleViewList = findViewById(R.id.clientList);
        assert recycleViewList != null;

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        ((RecyclerView) recycleViewList).setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        ((RecyclerView) recycleViewList).setLayoutManager(mLayoutManager);

        setUpAllGames((RecyclerView) recycleViewList);
        loadEvents();

    }


    @Override
    public void showError(String message) {
        progressBar.setVisibility(View.GONE);
        Log.i(TAG, "In show error ... ");
//        Snackbar.make(recycleViewList, error, Snackbar.LENGTH_INDEFINITE)
//                .setAction("RETRY", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        loadEvents();
//                    }
//                }).show();
    }

    private boolean loadEvents() {
        Log.i(TAG, " in load events .... ");
        boolean connectivity = manager.networkConnectivity(getApplicationContext());
        if (!connectivity) {
            Log.i(TAG, "No internet connection");
            showError("No internet connection!");
        }
        manager.loadAllForClient(progressBar, this);
        return connectivity;
    }

    @Override
    public void clear() {
        Log.i(TAG, " in clear() - clear the adapter ");
        adapter.clear();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.displayBought:
                Log.i(TAG, "Displaying the games that were BOUGHT...");
                text.setText("My Bought Games: ");
                break;
            case R.id.displayAllClient:
                Log.i(TAG, "Displaying ALL the game...");
                text.setText("All Games: ");
                setUpAllGames((RecyclerView) recycleViewList);
                loadEvents();
                break;

            case R.id.displayRented:
                Log.i(TAG, "Displaying the games that were RENTED...");
                text.setText("My Rented Games: ");
                break;
        }
    }

    private void setUpAllGames(final RecyclerView recyclerView) {
        adapter = new MyAdapter();

        ((GameApp) getApplication()).clientDatabase.getDao().getAll()
                .observe(this, new Observer<List<Game>>() {
                    @Override
                    public void onChanged(@Nullable List<Game> games) {
                        adapter.setData(games);
                        Log.i(TAG, "setting data IN setUpAllGames method: ");
                        Log.i(TAG, " FIRST ELEM IS : " + games.get(0).toString());

                    }
                });
        recyclerView.setAdapter(adapter);

    }
}
