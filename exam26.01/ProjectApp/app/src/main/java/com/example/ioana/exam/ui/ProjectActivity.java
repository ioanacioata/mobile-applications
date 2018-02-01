package com.example.ioana.exam.ui;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ioana.exam.R;
import com.example.ioana.exam.config.AppDatabase;
import com.example.ioana.exam.config.MyCallback;
import com.example.ioana.exam.config.Util;
import com.example.ioana.exam.domain.Project;
import com.example.ioana.exam.service.MyService;
import com.example.ioana.exam.service.ServiceFactory;
import com.example.ioana.exam.ui.adapter.Adapter2;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectActivity extends AppCompatActivity implements MyCallback, View.OnClickListener {

    private static final String TAG = ProjectActivity.class.getName();
    private static Handler handler;
    private ProgressDialog progressDialog;
    private Adapter2 adapter;
    private RecyclerView recycleView;
    private List<Project> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        handler = new Handler();

        progressDialog = new ProgressDialog(this);

        findViewById(R.id.redirectToPromoteBtn).setOnClickListener(this);

//        set up the list

        recycleView = findViewById(R.id.clientList);
        assert recycleView != null;
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycleView.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(mLayoutManager);

        getAll();

    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getAll() {
        Log.i(TAG, "In get all ");
        if (isNetworkAvailable()) {

            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();


            MyService service = ServiceFactory.createRetrofitService(MyService.class);
            Call<List<Project>> call = service.getProjects();
            call.enqueue(new Callback<List<Project>>() {
                @Override
                public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                    list = response.body();
                    if (progressDialog.isShowing()) {
                        Log.i(TAG, "DISMISSSSSSSSSSSSSSS");
                        progressDialog.dismiss();
                    }
                    if (response.code() == 200) {
                        //populate list
                        adapter = new Adapter2(list, getApplicationContext());

                        recycleView.setAdapter(adapter);
                        final AppDatabase db = getDatabase();
                        db.getDao().deleteAll();
                        for (Project p : list) {
                            db.getDao().add(p);
                        }
                        showSuccess("getAll();", "  Response: " + response.message());
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        String msg = Util.getErrorMessage2(response);

                        showError("getAll() -> onResponse()",
                                "Status : " + response.code() + " Message : " + response.message());

                    }
                }

                @Override
                public void onFailure(Call<List<Project>> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    showError("getAll() -> on Failure()", t.getMessage());
                }
            });

        } else {

            reloadData();
        }
    }

    private void reloadData() {
        showError("getAll()", "No internet connection!");

        final AppDatabase db = getDatabase();
        list = db.getDao().getAll();
        adapter = new Adapter2(list, getApplicationContext());
        recycleView.setAdapter(adapter);

        //try to reload data
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Trying to reload data");
                getAll();
                handler.postDelayed(this, 100000);
            }
        }, 20000);
    }

    @NonNull
    private AppDatabase getDatabase() {
        return Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "projectDatabase")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "ON RESUMEEEEEEEEEEEEEEEE");
        adapter = new Adapter2(list, getApplicationContext());
        getAll();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i(TAG, "Disabling handler and going back to main activity");
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.redirectToPromoteBtn:
                Log.i(TAG, "pressed add button ...");
                if (isNetworkAvailable()) {
                    //todo redirect to promote
                    Intent i = new Intent(this, PromoteActivity.class);
                    startActivity(i);
                } else {
                    showError("pressed add1Btn", "No internet connection! Cannot add.");
                }
                break;
        }
    }


    //MyCallback IMPLEMENTATION

    @Override
    public void showError(String location, String message) {
        Log.i(TAG, "In show error ... ");
        Log.e(TAG, "LOCATION: " + location + "  MESSAGE: " + message);

        Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void clear() {
        Log.i(TAG, " in clear() -  ");

    }

    @Override
    public void showSuccess(String location, String message) {
        Log.i(TAG, " successful operation ... ");
        Log.i(TAG, "LOCATION: " + location + "  MESSAGE: " + message);

        if (progressDialog.isShowing()) {
            Log.i(TAG, "dismissssss");
            progressDialog.dismiss();
        }
        progressDialog.dismiss();

    }
}
