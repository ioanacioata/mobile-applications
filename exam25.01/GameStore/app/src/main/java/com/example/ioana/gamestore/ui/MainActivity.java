package com.example.ioana.gamestore.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ioana.gamestore.R;

/**
 * MainActivity contains 2 buttons to redirect to the Client Section or the Employee Section.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.goToClientButton).setOnClickListener(this);
        findViewById(R.id.goToEmployeeButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goToClientButton:
                Log.i(TAG, "goToClient button pressed -> going to activity1");
                startActivity(new Intent(this, ClientActivity.class));
                break;
            case R.id.goToEmployeeButton:
                Log.i(TAG, "goToEmployee button pressed -> going to activity2");
                startActivity(new Intent(this, EmployeeActivity.class));
                break;
        }
    }
}
