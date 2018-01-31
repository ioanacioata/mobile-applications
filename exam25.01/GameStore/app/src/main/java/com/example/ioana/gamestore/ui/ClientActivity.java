package com.example.ioana.gamestore.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ioana.gamestore.R;
import com.example.ioana.gamestore.config.GameApp;
import com.example.ioana.gamestore.config.Manager;
import com.example.ioana.gamestore.config.MyCallback;
import com.example.ioana.gamestore.dao.GameDao;
import com.example.ioana.gamestore.ui.adapter.ClientAdapter;

public class ClientActivity extends AppCompatActivity implements MyCallback, View.OnClickListener {

    private static final String TAG = ClientActivity.class.getName();
    TextView text;
    ProgressDialog progressDialog;
    private Manager manager;

    private ClientAdapter adapter;
    private View recycleViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        manager = new Manager(getApplication());

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");


        text = findViewById(R.id.currentListText);
        text.setText("Click on the button to choose what to display ... ");

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

        adapter = new ClientAdapter();
    }


    @Override
    public void showError(String message) {
        Log.i(TAG, "In show error ... ");
        Snackbar.make(recycleViewList, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setUpAllGames((RecyclerView) recycleViewList);
                    }
                }).show();
    }

    /**
     * Load data from the server
     *
     * @return true - if we have internet connection, false otherwise
     */
    private boolean loadEvents() {
        Log.i(TAG, " in load events .... ");
        boolean connectivity = manager.networkConnectivity(getApplicationContext());
        if (!connectivity) {
            Log.i(TAG, "No internet connection");
            showError("No internet connection!");
        } else {
            manager.loadAllForClient(progressDialog, this);
        }
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
                text.setText("My bought games: ");
                this.clear();
                setUpGames((RecyclerView) recycleViewList, ((GameApp) getApplication()).buyDatabase.getDao());
                break;
            case R.id.displayAllClient:
                Log.i(TAG, "Displaying ALL the game...");

                setUpAllGames((RecyclerView) recycleViewList);

                break;

            case R.id.displayRented:
                Log.i(TAG, "Displaying the games that were RENTED...");
                text.setText("My rented games: ");
                this.clear();
                final GameDao rentDao = ((GameApp) getApplication()).rentDatabase.getDao();
                setUpGames((RecyclerView) recycleViewList, rentDao);
                break;
        }
    }

    private void setUpGames(RecyclerView recyclerView, final GameDao gameDao) {
        Log.i(TAG, "In set up RENT/BOUGHT games... ");

        adapter = new ClientAdapter();
        adapter.setData(gameDao.getAll());
        recyclerView.setAdapter(adapter);

    }

    private void setUpAllGames(final RecyclerView recyclerView) {
        Log.i(TAG, "In set up ALL games... ");

        text.setText("All available games: ");

        if (loadEvents()) {
            adapter = new ClientAdapter();
            adapter.setData(((GameApp) getApplication()).clientDatabase.getDao().getAll());
            recyclerView.setAdapter(adapter);
        }
    }
}
