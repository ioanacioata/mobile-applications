package com.example.ioana.exam.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ioana.exam.R;
import com.example.ioana.exam.config.MyCallback;
import com.example.ioana.exam.config.Util;
import com.example.ioana.exam.domain.Text;
import com.example.ioana.exam.service.MyService;
import com.example.ioana.exam.service.ServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener, MyCallback {

    private static final String TAG = AdminActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        findViewById(R.id.seeConfirmedButton).setOnClickListener(this);
        findViewById(R.id.seeTakenButton).setOnClickListener(this);
        findViewById(R.id.deleteAllSeatsButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.seeConfirmedButton:
                Log.i(TAG, "seeConfirmedButton pressed....");

                if (isNetworkAvailable()) {
                    Intent i = new Intent(getApplicationContext(), AdminListActivity.class);
                    i.putExtra(AdminListActivity.CALL, AdminListActivity.CONFIRMED);
//                finish();
                    startActivity(i);
                } else {
                    showError("pressed seeConfirmedButton", "No internet connection! Cannot see" +
                            " confirmed list.");
                }

                break;

            case R.id.seeTakenButton:
                Log.i(TAG, "seeTakenButton pressed....");
                if (isNetworkAvailable()) {
                    Intent i = new Intent(getApplicationContext(), AdminListActivity.class);
                    i.putExtra(AdminListActivity.CALL, AdminListActivity.TAKEN);
//                finish();
                    startActivity(i);
                } else {
                    showError("pressed seeConfirmedButton", "No internet connection! Cannot see" +
                            " confirmed list.");
                }

                break;

            case R.id.deleteAllSeatsButton:
                Log.i(TAG, "deleteAllSeatsButton pressed....");

                if (isNetworkAvailable()) {
                    MyService service = ServiceFactory.createRetrofitService(MyService.class);
                    Call<Text> call = service.deleteAll();
                    call.enqueue(new Callback<Text>() {
                        @Override
                        public void onResponse(Call<Text> call, Response<Text> response) {
                            if (response.code() == 200) {
                                showSuccess("deleteBtn->onResponse()", response.body().getText());
                            } else {
                                String msg = Util.getErrorMessage3(response);
                                showError("deleteBtn->onResponse()", msg);
                            }
                        }

                        @Override
                        public void onFailure(Call<Text> call, Throwable t) {
                            showError("deleteBtn->onFailure()", t.getMessage());
                        }
                    });

                } else {
                    showError("pressed deleteBtn", "No internet connection! Cannot delete.");
                }
                break;

        }
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    //MyCallback IMPLEMENTATION

    @Override
    public void showError(String location, String message) {
        Log.i(TAG, "In show error ... ");
        Log.e(TAG, "LOCATION: " + location + "  MESSAGE: " + message);

        Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void clear() {
        Log.i(TAG, " in clear() -  ");

    }

    @Override
    public void showSuccess(String location, String message) {
        Log.i(TAG, " successful operation ... ");
        Log.i(TAG, "LOCATION: " + location + "  MESSAGE: " + message);

        Toast.makeText(getApplicationContext(), "Success: " + message, Toast.LENGTH_LONG).show();

    }
}
