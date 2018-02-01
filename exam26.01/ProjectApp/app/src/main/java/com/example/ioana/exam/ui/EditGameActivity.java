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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ioana.exam.R;
import com.example.ioana.exam.config.MyCallback;
import com.example.ioana.exam.domain.Project;
import com.example.ioana.exam.domain.Status;
import com.example.ioana.exam.domain.StatusConverter;
import com.example.ioana.exam.domain.Type;
import com.example.ioana.exam.domain.TypeConv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditGameActivity extends AppCompatActivity implements View.OnClickListener, MyCallback {
    public static final String DELETE_IDEA = "deleteIdea";
    public static final String PROJECT = "project";
    public static final String ACTION = "action";
    private static final String TAG = EditGameActivity.class.getName();
    ProgressDialog progressDialog;

    private Project project;
    private String action;

    private Spinner statusSpinner;
    private Spinner typeSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);


        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        Button addBtn = findViewById(R.id.addBtn);
        Button deleteBtn = findViewById(R.id.deleteBtn);

        action = getIntent().getStringExtra(ACTION);
        switch (action) {
            case DELETE_IDEA:
                deleteBtn.setVisibility(View.VISIBLE);
                project = (Project) getIntent().getSerializableExtra(PROJECT);

                //populate fields
                ((TextView) findViewById(R.id.idGame)).setText(String.valueOf(project.getId()));
                ((EditText) findViewById(R.id.nameGame)).setText(project.getName());
                ((EditText) findViewById(R.id.budgetGame)).setText(String.valueOf(project.getBudget()));
                break;

            default:
                project = new Project();
        }


        //populate the spinners
        statusSpinner = findViewById(R.id.statusGame);
        typeSpinner = findViewById(R.id.typeGame);
        populateStatusSpinner();
        populateTypeSpinner();

        //add actions for buttons
        addBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    private void populateStatusSpinner() {
        List<Status> list = Arrays.asList(Status.values());
        List<String> spinnerList = new ArrayList<>();
        int position = 0;
        for (Status s : list) {
            spinnerList.add(s.toString());
            if (project.getStatus().equals(s)) {
                Log.i(TAG, "current status: " + s.toString());
                position = spinnerList.size() - 1;
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerList);
        statusSpinner.setAdapter(arrayAdapter);
        statusSpinner.setSelection(position);

    }

    private void populateTypeSpinner() {
        List<Type> list = Arrays.asList(Type.values());
        List<String> spinnerList = new ArrayList<>();
        int position = 0;
        for (Type type : list) {
            spinnerList.add(type.toString());
            if (project.getType().equals(type)) {
                Log.i(TAG, "current type: " + type.toString());
                position = spinnerList.size() - 1;
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerList);
        typeSpinner.setAdapter(arrayAdapter);
        typeSpinner.setSelection(position);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        int id = 0;
        String name = String.valueOf(((EditText) findViewById(R.id.nameGame)).getText());
        Integer budget = Integer.valueOf(String.valueOf(((EditText) findViewById(R.id.budgetGame)).getText()));
        Status status = StatusConverter.toType(String.valueOf(statusSpinner.getSelectedItem()));
        Type type = TypeConv.toType(String.valueOf(typeSpinner.getSelectedItem()));
        Project newProject = new Project(id, name, type, status, budget);
        switch (view.getId()) {
            case R.id.addBtn:
                Log.i(TAG, "pressed ADD " + newProject.toString());
                progressDialog.show();
                //todo dismis progress dialog => errors


                finish();
                intent = new Intent(this, IdeaActivity.class);
                startActivity(intent);
                break;

            case R.id.deleteBtn:
                newProject.setId(project.getId());
                Log.i(TAG, "pressed DELETE " + newProject.toString());
                if (isNetworkAvailable()) {
                    progressDialog.show();
                    //todo dismis progress dialog => errors
                    showSuccess("On pressing delete ", newProject.getName() + "was deleted");
                } else {
                    showError("On pressing delete ", "No internet connection! Cannot delete.");
                }

                intent = new Intent(this, IdeaActivity.class);
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
        Log.i(TAG, "action : " + action + " showing error ...");
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

    @Override
    public void clear() {
        Log.i(TAG, "action : " + action + " clearing ...");
        finish();
    }

    @Override
    public void showSuccess(String location, final String message) {
        Log.i(TAG, " successful operation ... " + message);
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
//        Toast.makeText(getApplicationContext(), "Success: " + message, Toast.LENGTH_LONG).show();
    }
}
