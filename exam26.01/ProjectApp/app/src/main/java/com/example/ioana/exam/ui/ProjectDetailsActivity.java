package com.example.ioana.exam.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ioana.exam.R;
import com.example.ioana.exam.config.MyCallback;
import com.example.ioana.exam.config.Util;
import com.example.ioana.exam.domain.Project;
import com.example.ioana.exam.service.MyService;
import com.example.ioana.exam.service.ServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectDetailsActivity extends AppCompatActivity implements View.OnClickListener, MyCallback {
    public static final String PROJECT = "project";
    private static final String REMOVE_PROJECT = "removeProject call()";
    private static final String APPROVE_PROJECT = "approveProject call()";
    private static final String DISCARD_PROJECT = "discardProject call()";
    private static final String TAG = ProjectDetailsActivity.class.getName();
    ProgressDialog progressDialog;

    private Project project;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_project);

        project = (Project) getIntent().getSerializableExtra(PROJECT);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");


        //populate fields
        ((TextView) findViewById(R.id.idGame2)).setText(String.valueOf(project.getId()));
        ((TextView) findViewById(R.id.nameGame2)).setText(project.getName());
        ((TextView) findViewById(R.id.budgetGame2)).setText(String.valueOf(project.getBudget()));
        ((TextView) findViewById(R.id.statusGame2)).setText(project.getStatus().toString());
        ((TextView) findViewById(R.id.typeGame2)).setText(project.getType().toString());

        //add actions for buttons
        findViewById(R.id.approveBtn).setOnClickListener(this);
        findViewById(R.id.discardBtn).setOnClickListener(this);
        findViewById(R.id.removeBtn).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.approveBtn:
                Log.i(TAG, "pressed APPROVE " + project.toString());
                progressDialog.show();

                //todo add call - POST
                if (isNetworkAvailable()) {

                    MyService service = ServiceFactory.createRetrofitService(MyService.class);
                    Call<Project> call = service.approveProject(project);
                    call.enqueue(new Callback<Project>() {
                        @Override
                        public void onResponse(Call<Project> call, Response<Project> response) {
                            if (response.code() == 200) {
                                Project p = response.body();
                                //could add to db
                                showSuccess(APPROVE_PROJECT + "->on response ", p.getName() + " was" +
                                        " approved");
                            } else {
                                String msg = Util.getErrorMessage(response);
                                showError(APPROVE_PROJECT + "->on response", "Status: " + response
                                        .code() + " " + "Message: " + msg);
                            }
                        }

                        @Override
                        public void onFailure(Call<Project> call, Throwable t) {
                            showError(APPROVE_PROJECT + "->on failure", t.getMessage());
                        }
                    });

                } else {
                    showError("On pressing approve ", "No internet connection! Cannot approve.");
                }

                finish();
                intent = new Intent(this, ProjectActivity.class);
                startActivity(intent);
                break;

            case R.id.discardBtn:
                Log.i(TAG, "pressed DISCARD " + project.toString());
                progressDialog.show();

                if (isNetworkAvailable()) {

                    MyService service = ServiceFactory.createRetrofitService(MyService.class);
                    Call<Project> call = service.discardProject(project);
                    call.enqueue(new Callback<Project>() {
                        @Override
                        public void onResponse(Call<Project> call, Response<Project> response) {
                            if (response.code() == 200) {
                                Project p = response.body();
                                //could add to db
                                showSuccess(DISCARD_PROJECT + "->on response ", p.getName() + " was" +
                                        " discarded.");
                            } else {
                                String msg = Util.getErrorMessage(response);
                                showError(DISCARD_PROJECT + "->on response", "Status: " + response
                                        .code() + " " + "Message: " + msg);
                            }
                        }

                        @Override
                        public void onFailure(Call<Project> call, Throwable t) {
                            showError(DISCARD_PROJECT + "->on failure", t.getMessage());
                        }
                    });

                } else {
                    showError("On pressing discard ", "No internet connection! Cannot discard.");
                }

                finish();
                intent = new Intent(this, ProjectActivity.class);
                startActivity(intent);
                break;

            case R.id.removeBtn:

                progressDialog.show();
                Log.i(TAG, "pressed REMOVE " + project.toString());
                if (isNetworkAvailable()) {
                    //todo delete call - DELETE
                    MyService service = ServiceFactory.createRetrofitService(MyService.class);
                    Call<Project> call = service.deleteIdea(project.getId());

                    call.enqueue(new Callback<Project>() {
                        @Override
                        public void onResponse(Call<Project> call, Response<Project> response) {
                            if (response.code() == 200) {
                                Project resposeProject = response.body();
                                showSuccess(REMOVE_PROJECT + "->onResponse ", resposeProject.getName
                                        () + "was removed.");
                            } else {
                                String msg = Util.getErrorMessage(response);
                                showError(REMOVE_PROJECT + "->onResponse ", msg);
                            }
                        }

                        @Override
                        public void onFailure(Call<Project> call, Throwable t) {
                            showError(REMOVE_PROJECT + "->onFailure ", t.getMessage());
                        }
                    });

                } else {
                    showError("On pressing remove ", "No internet connection! Cannot remove.");
                }

                finish();
                intent = new Intent(this, ProjectActivity.class);
                startActivity(intent);
                break;
        }
    }


    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void showError(String location, final String message) {
        Log.i(TAG, " showing error ...");
        Log.e(TAG, "LOCATION: " + location + "  MESSAGE: " + message);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    @Override
    public void clear() {
        Log.i(TAG, " clearing ...");


    }

    @Override
    public void showSuccess(String location, final String message) {
        Log.i(TAG, " successful operation ... ");
        Log.i(TAG, "LOCATION: " + location + "  MESSAGE: " + message);

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Success: " + message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
