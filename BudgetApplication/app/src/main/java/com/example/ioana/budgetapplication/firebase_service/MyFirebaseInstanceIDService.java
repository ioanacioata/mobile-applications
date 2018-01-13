package com.example.ioana.budgetapplication.firebase_service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Ioana on 13/01/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    public static final String TAG = MyFirebaseInstanceIDService.class.getName();

    @Override
    public void onTokenRefresh() {
        //Get updated token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "New token: " + refreshedToken);

        //You can save the token into this third party server to do anything you want

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
    }
}
