package com.example.ioana.exam.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.ioana.exam.R;

/**
 * MainActivity contains 2 buttons to redirect to the Idea Section or the Project Section.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.goToSection1Button).setOnClickListener(this);
        findViewById(R.id.goToSection2Button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goToSection1Button:
                Log.i(TAG, "goToIdea button pressed -> going to activity1");
                startActivity(new Intent(this, IdeaActivity.class));
                break;
            case R.id.goToSection2Button:
                Log.i(TAG, "goToProject button pressed -> going to activity2");
                startActivity(new Intent(this, ProjectActivity.class));
                break;
        }
    }
}
