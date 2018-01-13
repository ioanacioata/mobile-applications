package com.example.ioana.budgetapplication.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ioana.budgetapplication.R;
import com.example.ioana.budgetapplication.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextEmail;
    EditText editTextPassword;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    UserRepository userRepository = new UserRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        editTextEmail = findViewById(R.id.emailEditTextSignUp);
        editTextPassword = findViewById(R.id.passwordEditTextSignUp);
        progressBar = findViewById(R.id.progressbarSignUp);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.textViewBackToLogin).setOnClickListener(this);
        findViewById(R.id.buttonSignUp).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSignUp:
                registerUser();
                break;
            case R.id.textViewBackToLogin:
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum lenght of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    if (email.equals("admin@budgetapp.com")) {
                        //hardcoded admin
                        userRepository.addAdmin(mAuth.getCurrentUser().getUid(), email);
                        Log.d("SIGN UP ", " before is admin call");
                        Log.d("SIGNUP :", "IS ADMINNNNN " + userRepository.isAdmin(mAuth.getCurrentUser().getUid()));
                        finish();
                        startActivity(new Intent(SignUpActivity.this, UsersActivity.class));
                    } else {
                        //add the user to the users table, with the role USER
                        userRepository.addUser(mAuth.getCurrentUser().getUid(), email);
                        Log.e("SIGN UP ", " before is admin call");

                        Log.e("SIGNUP :", "IS ADMINNNNN " + userRepository.isAdmin(mAuth.getCurrentUser().getUid()));
                        finish();
                        startActivity(new Intent(SignUpActivity.this, ProfileActivity.class));
                    }

                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}
