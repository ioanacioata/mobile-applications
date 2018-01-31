package com.example.ioana.gamestore.config;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.ioana.gamestore.domain.Game;
import com.example.ioana.gamestore.service.Service;
import com.example.ioana.gamestore.service.ServiceFactory;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

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
     * @param progressBar
     * @param callback
     */
    public void loadAllForClient(final ProgressBar progressBar, final MyCallback callback) {
        Log.i(TAG, "Loading data ... ");
        progressBar.setVisibility(View.VISIBLE);
        service.getGames()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Game>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "Service completed!");
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Errors while loading the events:   ", e);
                        callback.showError("Not able to retrieve the data. Displaying local data!");
                    }

                    @Override
                    public void onNext(final List<Game> games) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // update device database
                                app.clientDatabase.getDao().deleteAll();

                                //just print current data
                                for (Game g : games) {
                                    Log.i(TAG, g.toString());
                                    app.clientDatabase.getDao().add(g);
                                }
//                                Timber.i("SIZE IN THE DB  : %s", app.clientDatabase.getDao()
//                                        .getAll().getValue().size());
//                                app.clientDatabase.getDao().addAll(games);
                            }
                        }).start();
                        Log.i(TAG, "Data persisted");
                    }
                });
    }

    /**
     * Load data from /all API and to employeeDatabase
     *
     * @param progressBar
     * @param callback
     */
    public void loadAllForEmployee(final ProgressBar progressBar, final MyCallback callback) {
        Log.i(TAG, "Loading data ... ");
        service.getAllGames()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Game>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "Service completed!");
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Errors while loading the events:   ", e);
                        callback.showError("Not able to retrieve the data. Displaying local data!");
                    }

                    @Override
                    public void onNext(final List<Game> games) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //update device database
                                app.employeeDatabase.getDao().deleteAll();
                                app.employeeDatabase.getDao().addAll(games);
                            }
                        }).start();
                        Log.i(TAG, "Data persisted");
                    }
                });
    }

}
