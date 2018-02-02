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
import com.example.ioana.exam.config.Util;
import com.example.ioana.exam.domain.Seat;
import com.example.ioana.exam.domain.Status;
import com.example.ioana.exam.domain.Type;
import com.example.ioana.exam.service.MyService;
import com.example.ioana.exam.service.ServiceFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientDetailsActivity extends AppCompatActivity implements View.OnClickListener, MyCallback {
   public static final String PROJECT = "seat";
    public static final String ACTION = "action";
    public static final String REZERVE_SEAT = "reserveSeat";
    private static final String TAG = ClientDetailsActivity.class.getName();
    private static final String BUY_SEAT = "buySeat";
    ProgressDialog progressDialog;

    private Seat seat;
    private String action;

    private Spinner statusSpinner;
    private Spinner typeSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_client);


        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        Button buybtn = findViewById(R.id.buyBtn);
        Button reserveBtn = findViewById(R.id.reserveBtn);


        seat = (Seat) getIntent().getSerializableExtra(PROJECT);

        //populate fields
        ((TextView) findViewById(R.id.idGame)).setText(String.valueOf(seat.getId()));
        ((EditText) findViewById(R.id.nameGame)).setText(seat.getName());


        //populate the spinners
        statusSpinner = findViewById(R.id.statusGame);
        typeSpinner = findViewById(R.id.typeGame);
        populateStatusSpinner();
        populateTypeSpinner();

        //add actions for buttons
        buybtn.setOnClickListener(this);
        reserveBtn.setOnClickListener(this);
    }

    private void populateStatusSpinner() {
        List<Status> list = Arrays.asList(Status.values());
        List<String> spinnerList = new ArrayList<>();
        int position = 0;
        for (Status s : list) {
            spinnerList.add(s.toString());
            if (seat.getStatus().equals(s)) {
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
            if (seat.getType().equals(type)) {
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
        switch (view.getId()) {
            case R.id.reserveBtn:
                Log.i(TAG, "pressed RESERVE  " + seat.toString());
                progressDialog.show();

                if (isNetworkAvailable()) {
                    progressDialog.show();

                    MyService service = ServiceFactory.createRetrofitService(MyService.class);
                    Call<Seat> call = service.reserveSeat(seat);
                    call.enqueue(new Callback<Seat>() {
                        @Override
                        public void onResponse(Call<Seat> call, Response<Seat> response) {
                            if (response.code() == 200) {
                                Seat p = response.body();
                                //could add to db
                                showSuccess(REZERVE_SEAT + "->on response ", p.getName() + " was" +
                                        " reserved");
                            } else {
                                String msg = Util.getErrorMessage(response);
                                showError(REZERVE_SEAT + "->on response", "Status: " + response
                                        .code() + " " + "Message: " + msg);
                            }
                        }

                        @Override
                        public void onFailure(Call<Seat> call, Throwable t) {
                            showError(REZERVE_SEAT + "->on failure", t.getMessage());
                        }
                    });

                } else {
                    showError("On pressing reserve ", "No internet connection! Cannot reserve.");
                }

                finish();
                intent = new Intent(this, ClientActivity.class);
                startActivity(intent);
                break;

            case R.id.buyBtn:
                Log.i(TAG, "pressed BUY " + seat.toString());
                progressDialog.show();

                if (isNetworkAvailable()) {
                    progressDialog.show();

                    MyService service = ServiceFactory.createRetrofitService(MyService.class);
                    Call<Seat> call = service.buySeat(seat);
                    call.enqueue(new Callback<Seat>() {
                        @Override
                        public void onResponse(Call<Seat> call, Response<Seat> response) {
                            if (response.code() == 200) {
                                Seat p = response.body();
                                //could add to db
                                showSuccess(BUY_SEAT + "->on response ", p.getName() + " was" +
                                        " bought");
                            } else {
                                String msg = Util.getErrorMessage(response);
                                showError(BUY_SEAT + "->on response", "Status: " + response
                                        .code() + " " + "Message: " + msg);
                            }
                        }

                        @Override
                        public void onFailure(Call<Seat> call, Throwable t) {
                            showError(BUY_SEAT + "->on failure", t.getMessage());
                        }
                    });

                } else {
                    showError("On pressing buy ", "No internet connection! Cannot buy.");
                }

                finish();
                intent = new Intent(this, ClientActivity.class);
                intent = new Intent(this, ClientActivity.class);
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
    protected void onDestroy() {
        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }
        super.onDestroy();
    }

    //MyCallback implemnetation

    @Override
    public void showError(String location, final String message) {
        Log.i(TAG, "action : " + action + " showing error ...");
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
        Log.i(TAG, "action : " + action + " clearing ...");


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
//        Toast.makeText(getApplicationContext(), "Success: " + message, Toast.LENGTH_LONG).show();
    }

}
