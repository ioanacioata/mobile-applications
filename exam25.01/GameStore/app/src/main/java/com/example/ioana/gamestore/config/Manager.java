package com.example.ioana.gamestore.config;

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

import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Ioana on 30/01/2018.
 */

public class Manager {
    private static final String TAG = Manager.class.getName();
    private Service service;
    //todo add   private BookApp app; field -- using room

    public Manager() {
        this.service = ServiceFactory.createRetrofitService(Service.class, Service.SERVICE_ENDPOINT);
    }

    public boolean networkConnectivity(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void loadEvents(final ProgressBar progressBar/*, final MyCallback callback*/) {
        Log.i(TAG, "Loading data ... ");
        service.getGames()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Game>>() {
                    @Override
                    public void onCompleted() {
                        Timber.v("Service completed!");
                        Log.i(TAG, "Service completed!");
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "Errors while loading the events");
                        Log.e(TAG, "Errors while loading the events:   ", e);
                        //TODO implement callback and throw err message
//                        callback.showError("Not able to retrieve the data. Displaying local data!");
                    }

                    @Override
                    public void onNext(final List<Game> games) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (Game g : games) {
                                    Timber.i(g.toString());
                                    Log.i(TAG, g.toString());
                                }
                                //TODO update device database
                            }
                        }).start();
                        Timber.i("Data persisted");
                    }
                });
    }

}
