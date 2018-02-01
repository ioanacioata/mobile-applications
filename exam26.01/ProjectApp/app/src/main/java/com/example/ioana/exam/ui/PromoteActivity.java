package com.example.ioana.exam.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.ioana.exam.R;
import com.example.ioana.exam.config.AppDatabase;
import com.example.ioana.exam.config.MyCallback;
import com.example.ioana.exam.config.Util;
import com.example.ioana.exam.domain.Project;
import com.example.ioana.exam.service.MyService;
import com.example.ioana.exam.service.ServiceFactory;
import com.example.ioana.exam.ui.adapter.Adapter3;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromoteActivity extends AppCompatActivity implements MyCallback {

    private static final String TAG = PromoteActivity.class.getName();
    private static Handler handler;
    private ProgressDialog progressDialog;
    private Adapter3 adapter;
    private RecyclerView recycleView;
    private List<Project> list;

    public static void promote(Project project, Context context) {
        Log.i(TAG, "in promote method - project :  " + project.toString());
        MyService service = ServiceFactory.createRetrofitService(MyService.class);
        Call<Project> call = service.promoteIdea(project);
        call.enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                if (response.code() == 200) {
                    Log.i(TAG, "Success");
                    Toast.makeText(context, "Idea " + response.body().getName() + " promoted!", Toast
                            .LENGTH_LONG)
                            .show();

                    Log.i(TAG, "finishing promote activity! ");
                    //finish Activity
                    ((Activity) context).finish();
                } else {
                    Log.i(TAG, "problem... ");
                    Toast.makeText(context, "Idea not promoted: " + Util.getErrorMessage
                            (response), Toast
                            .LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<Project> call, Throwable t) {
                Log.i(TAG, "fail -> onFailure()... ");
                Toast.makeText(context, "Error: " + t.getMessage(), Toast
                        .LENGTH_LONG)
                        .show();
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promote);

        handler = new Handler();

        progressDialog = new ProgressDialog(this);


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
            Call<List<Project>> call = service.getIdeas();
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
                        adapter = new Adapter3(list, getApplicationContext());

                        recycleView.setAdapter(adapter);
                        final AppDatabase db = getIdeaDatabase();
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

        final AppDatabase db = getIdeaDatabase();
        list = db.getDao().getAll();
        adapter = new Adapter3(list, getApplicationContext());
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
    private AppDatabase getIdeaDatabase() {
        return Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "ideaDatabase")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "ON RESUMEEEEEEEEEEEEEEEE");
        adapter = new Adapter3(list, getApplicationContext());
        getAll();

    }


    //MyCallback IMPLEMENTATION

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i(TAG, "Disabling handler and going back to main activity");
        handler.removeCallbacksAndMessages(null);
    }

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
