package com.example.ioana.gamestore.config;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.ioana.gamestore.domain.Game;
import com.example.ioana.gamestore.service.Service;
import com.example.ioana.gamestore.service.ServiceFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Manages the persistance and hangles the API calls
 * Populates the correct database in each case
 */
public class Manager {
    private static final String TAG = Manager.class.getName();
    private GameApp app;
    private Service service;

    public Manager(Application app) {
        this.app = (GameApp) app;
        this.service = ServiceFactory.createRetrofitService(Service.class, Service.SERVICE_ENDPOINT);
    }

    /**
     * @param context
     * @return true if online, false if offline
     */
    public boolean networkConnectivity(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * Load data from /games API and to clientDatabase
     *
     * @param progressDialog
     * @param callback
     */
    public void loadAllForClient(final ProgressDialog progressDialog, final MyCallback callback) {
        Log.i(TAG, "Loading data ... ");

        progressDialog.show();

        Call<List<Game>> call = service.getGames();

        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                List<Game> games = response.body();
                Log.i(TAG, "Size of client list is " + games.size());
                app.clientDatabase.getDao().deleteAll();
                app.clientDatabase.getDao().addAll(games);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Log.e(TAG, "error in get all for clients", t);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(app.getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Load data from /all API and to employeeDatabase
     *
     * @param progressDialog
     * @param callback
     */
    public void loadAllForEmployee(final ProgressDialog progressDialog, final MyCallback callback) {
        Log.i(TAG, "Loading data for employee ... ");
        progressDialog.show();

        Call<List<Game>> call = service.getAllGames();

        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {

                List<Game> games = response.body();
                Log.i(TAG, "Size of employee list is " + games.size());
                app.employeeDatabase.getDao().deleteAll();
                app.employeeDatabase.getDao().addAll(games);
                Log.i(TAG, "Size of employee list DATABASE is " + app.employeeDatabase.getDao()
                        .getAll().size());

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                callback.clear();
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Log.e(TAG, "error in get all for employees", t);
                callback.showError(t.getMessage());
            }
        });
    }

    public void addGameEmployee(final MyCallback callback, final Game game) {
        Log.i(TAG, "adding game ... " + game.toString());
        Call<Game> call = service.addGame(game);

        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if (response.code() == 200) {
                    callback.showSuccess(game.toString() + " was added!");
                } else {
                    callback.showError("Status: " + response.code() + " Message : " + response.message());
                }
                callback.clear();
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                callback.showError("call : " + call.toString() + " throwable " + t.getMessage());
            }
        });
    }

    public void updateGameEmployee(final MyCallback callback, final Game game) {
        Log.i(TAG, "updating game ... " + game.toString());

        Call<Game> call = service.updateGame(game);

        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if (response.code() == 200) {
                    callback.showSuccess(game.toString() + " was updated!");
                } else {
                    callback.showError("Status: " + response.code() + " Message : " + response.message());
                }
                callback.clear();
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                callback.showError("call : " + call.toString() + " throwable " + t.getMessage());
            }
        });

    }

    public void deleteGameEmployee(final MyCallback callback, final Game game) {
        Log.i(TAG, "deleting game ... " + game.toString());
        Call<Game> call = service.deleteGame(game);

        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if (response.code() == 200) {
                    callback.showSuccess(game.toString() + " was deleted!");
                } else {
                    callback.showError("Status: " + response.code() + " Message : " + response.message());
                }
                callback.clear();
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                callback.showError("call : " + call.toString() + " throwable " + t.getMessage());
            }
        });
    }

}
