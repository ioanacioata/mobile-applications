package com.example.ioana.exam.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.ioana.exam.R;

/**
 * MainActivity contains 2 buttons to redirect to the Idea Section or the Seat Section.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.goToSection1Button).setOnClickListener(this);
        findViewById(R.id.goToSection2Button).setOnClickListener(this);
        findViewById(R.id.goToSection3Button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goToSection1Button:
                Log.i(TAG, "goToClient button pressed -> going to activity1");
                startActivity(new Intent(this, ClientActivity.class));
                break;
            case R.id.goToSection2Button:
                Log.i(TAG, "goToTheatre button pressed -> going to activity2");
                startActivity(new Intent(this, TheaterActivity.class));
                break;
            case R.id.goToSection3Button:
                Log.i(TAG, "goToAdmin button pressed -> going to activity3");
                startActivity(new Intent(this, AdminActivity.class));
                break;
        }
    }
}
