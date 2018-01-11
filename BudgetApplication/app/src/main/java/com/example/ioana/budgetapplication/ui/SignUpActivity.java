package com.example.ioana.budgetapplication.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.ioana.budgetapplication.R;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    EditText editTextEmail;
    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViewById(R.id.textViewBackToLogin).setOnClickListener(this);
        findViewById(R.id.buttonSignUp).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSignUp:
                registerUser();
                break;
            case  R.id.textViewBackToLogin:
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    private void registerUser() {

    }
}
