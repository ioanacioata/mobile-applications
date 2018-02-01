package com.example.ioana.gamestore.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ioana.gamestore.R;
import com.example.ioana.gamestore.config.Manager;
import com.example.ioana.gamestore.config.MyCallback;
import com.example.ioana.gamestore.domain.Game;
import com.example.ioana.gamestore.domain.Status;
import com.example.ioana.gamestore.domain.StatusConverter;
import com.example.ioana.gamestore.domain.Type;
import com.example.ioana.gamestore.domain.TypeConv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditGameActivity extends AppCompatActivity implements View.OnClickListener, MyCallback {
    private static final String TAG = EditGameActivity.class.getName();

    ProgressDialog progressDialog;

    private Game game;
    private String action;

    private Spinner statusSpinner;
    private Spinner typeSpinner;

    private Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);

        manager = new Manager(getApplication());

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        action = getIntent().getStringExtra("action");
        if (action.equals("edit")) {
            game = (Game) getIntent().getSerializableExtra("game");
            findViewById(R.id.addBtn).setVisibility(View.GONE);

            ((TextView) findViewById(R.id.idGame)).setText(String.valueOf(game.getId()));
            ((EditText) findViewById(R.id.nameGame)).setText(game.getName());
            ((EditText) findViewById(R.id.quantityGame)).setText(String.valueOf(game.getQuantity()));

        } else { //add
            game = new Game();
            findViewById(R.id.updateBtn).setVisibility(View.GONE);
            findViewById(R.id.deleteBtn).setVisibility(View.GONE);
        }

        statusSpinner = findViewById(R.id.statusGame);
        typeSpinner = findViewById(R.id.typeGame);
        populateStatusSpinner();
        populateTypeSpinner();

        findViewById(R.id.addBtn).setOnClickListener(this);
        findViewById(R.id.updateBtn).setOnClickListener(this);
        findViewById(R.id.deleteBtn).setOnClickListener(this);
    }

    private void populateStatusSpinner() {
        List<Status> list = Arrays.asList(Status.values());
        List<String> spinnerList = new ArrayList<>();
        int position = 0;
        for (Status s : list) {
            spinnerList.add(s.toString());
            if (game.getStatus().equals(s)) {
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
            if (game.getType().equals(type)) {
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
        Integer quantity = Integer.valueOf(String.valueOf(((EditText) findViewById(R.id.quantityGame)).getText()));
        Status status = StatusConverter.toType(String.valueOf(statusSpinner.getSelectedItem()));
        Type type = TypeConv.toType(String.valueOf(typeSpinner.getSelectedItem()));
        Game newGame = new Game(id, name, type, status, quantity);
        switch (view.getId()) {
            case R.id.addBtn:
                Log.i(TAG, "pressed ADD " + newGame.toString());
                progressDialog.show();
                manager.addGameEmployee(this, newGame);
                finish();
                intent = new Intent(this, EmployeeActivity.class);
                startActivity(intent);
                break;

            case R.id.updateBtn:
                newGame.setId(game.getId());
                Log.i(TAG, "pressed UPDATE " + newGame.toString());
                progressDialog.show();
                manager.updateGameEmployee(this, newGame);

                finish();
                intent = new Intent(this, EmployeeActivity.class);
                startActivity(intent);
                break;

            case R.id.deleteBtn:
                newGame.setId(game.getId());
                Log.i(TAG, "pressed DELETE " + newGame.toString());
                progressDialog.show();
                manager.deleteGameEmployee(this, newGame);

                finish();
                intent = new Intent(this, EmployeeActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void showError(String message) {
        Log.i(TAG, "action : " + action + " showing error ...");
        Snackbar.make(findViewById(R.id.nameGame), message, Snackbar.LENGTH_INDEFINITE)
                .setAction("DISMISS", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).show();
    }

    @Override
    public void clear() {
        Log.i(TAG, "action : " + action + " clearing ...");
        finish();
    }

    @Override
    public void showSuccess(String message) {
        Log.i(TAG, " successful operation ... ");
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        Toast.makeText(getApplicationContext(), "Success: " + message, Toast.LENGTH_LONG).show();
    }
}
