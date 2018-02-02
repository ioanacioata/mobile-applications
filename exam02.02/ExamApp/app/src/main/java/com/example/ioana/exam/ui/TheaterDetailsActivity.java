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
import com.example.ioana.exam.domain.Seat;
import com.example.ioana.exam.service.MyService;
import com.example.ioana.exam.service.ServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TheaterDetailsActivity extends AppCompatActivity implements View.OnClickListener, MyCallback {
    public static final String PROJECT = "seat";
    private static final String CONFIRM_SEAT_CALL = "confirmSeat call()";
    private static final String TAG = TheaterDetailsActivity.class.getName();
    ProgressDialog progressDialog;

    private Seat seat;

    @Override
    protected void onDestroy() {
        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_theater);

        seat = (Seat) getIntent().getSerializableExtra(PROJECT);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");


        //populate fields
        ((TextView) findViewById(R.id.idGame2)).setText(String.valueOf(seat.getId()));
        ((TextView) findViewById(R.id.nameGame2)).setText(seat.getName());
        ((TextView) findViewById(R.id.statusGame2)).setText(seat.getStatus().toString());
        ((TextView) findViewById(R.id.typeGame2)).setText(seat.getType().toString());

        //add actions for buttons
        findViewById(R.id.confirmBtn).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.confirmBtn:
                Log.i(TAG, "pressed CONFIRM " + seat.toString());
                progressDialog.show();

                if (isNetworkAvailable()) {
                    MyService service = ServiceFactory.createRetrofitService(MyService.class);
                    Call<Seat> call = service.confirmSeat(seat);
                    call.enqueue(new Callback<Seat>() {
                        @Override
                        public void onResponse(Call<Seat> call, Response<Seat> response) {
                            if (response.code() == 200) {
                                Seat p = response.body();
                                //could add to db
                                showSuccess(CONFIRM_SEAT_CALL + "->on response ", p.getName() + " was" +
                                        " confirmd");
                            } else {
                                String msg = Util.getErrorMessage(response);
                                showError(CONFIRM_SEAT_CALL + "->on response", "Status: " + response
                                        .code() + " " + "Message: " + msg);
                            }
                        }

                        @Override
                        public void onFailure(Call<Seat> call, Throwable t) {
                            showError(CONFIRM_SEAT_CALL + "->on failure", t.getMessage());
                        }
                    });

                } else {
                    showError("On pressing confirm ", "No internet connection! Cannot confirm.");
                }

                finish();
                intent = new Intent(this, TheaterActivity.class);
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
