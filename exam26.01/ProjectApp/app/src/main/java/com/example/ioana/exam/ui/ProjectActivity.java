package com.example.ioana.exam.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ioana.exam.R;
import com.example.ioana.exam.config.MyCallback;
import com.example.ioana.exam.ui.adapter.Adapter2;

public class ProjectActivity extends AppCompatActivity implements MyCallback {


    private static final String TAG = ProjectActivity.class.getName();
    private final Handler handler = new Handler();
    ProgressDialog progressDialog;
    private Adapter2 adapter;
    private View recycleViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);


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

    @Override
    protected void onResume() {
        super.onResume();
        setUpList((RecyclerView) recycleViewList);

    }

    private void setUpList(final RecyclerView recycleView) {

    }

    @Override
    public void showError(String location, String message) {
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
    public void showSuccess(String location, String message) {
        Log.i(TAG, " successful operation ... ");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i(TAG, "Disabling handler and going back to main activity");
        handler.removeCallbacksAndMessages(null);

    }
}
