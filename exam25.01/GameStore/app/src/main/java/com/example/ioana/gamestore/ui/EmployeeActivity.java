package com.example.ioana.gamestore.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ioana.gamestore.R;
import com.example.ioana.gamestore.config.GameApp;
import com.example.ioana.gamestore.config.Manager;
import com.example.ioana.gamestore.config.MyCallback;
import com.example.ioana.gamestore.ui.adapter.EmployeeAdapter;

public class EmployeeActivity extends AppCompatActivity implements MyCallback {


    private static final String TAG = EmployeeActivity.class.getName();
    private final Handler handler = new Handler();
    TextView text;
    ProgressDialog progressDialog;
    private Manager manager;
    private EmployeeAdapter adapter;
    private View recycleViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        manager = new Manager(getApplication());

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        recycleViewList = findViewById(R.id.clientList);
        assert recycleViewList != null;

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        ((RecyclerView) recycleViewList).setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        ((RecyclerView) recycleViewList).setLayoutManager(mLayoutManager);

        setUpList((RecyclerView) recycleViewList);

        findViewById(R.id.redirectToAddBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditGameActivity.class);
                intent.putExtra("action", "add");
                startActivity(intent);
            }
        });

    }

    private void setUpList(final RecyclerView recycleView) {
        if (manager.networkConnectivity(getApplicationContext())) {
            //then update
            manager.loadAllForEmployee(progressDialog, this);
            adapter = new EmployeeAdapter(getApplicationContext());
            adapter.setData(((GameApp) getApplication()).clientDatabase.getDao().getAll());
            recycleView.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "No internet connection.", Toast
                    .LENGTH_LONG)
                    .show();
            Log.i(TAG, "Check the connection");
            progressDialog.show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "Trying to reload the data");
                    setUpList(recycleView);
                    handler.postDelayed(this, 10000);
                }
            }, 20000); //milliseconds
        }
    }

    @Override
    public void showError(String message) {
        Log.i(TAG, " in show error ...");
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void clear() {
        adapter.clear();
    }

    @Override
    public void showSuccess(String message) {
        Log.i(TAG, " successful operation ... ");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i(TAG, "Disabling handler and going back to main activity");
        handler.removeCallbacksAndMessages(null);

    }
}
