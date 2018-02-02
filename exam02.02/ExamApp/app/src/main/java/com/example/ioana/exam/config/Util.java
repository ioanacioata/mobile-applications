package com.example.ioana.exam.config;

import android.util.Log;

import com.example.ioana.exam.domain.Seat;
import com.example.ioana.exam.domain.Text;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;



/**
 * Created by Ioana on 01/02/2018.
 */

public class Util {
    private static final String TAG = Util.class.getName();

    public static String getErrorMessage(Response<Seat> response) {
        String msg = "";
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            msg = jObjError.getString("text");
        } catch (JSONException | IOException e) {
            Log.e(TAG,"JSON EXCEPTION "+ e.getMessage());
            e.printStackTrace();
        }
        return msg;
    }


    public static String getErrorMessage2(Response<List<Seat>> response) {
        String msg = "";
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            msg = jObjError.getString("text");
        } catch (JSONException | IOException e) {
            Log.e(TAG,"JSON EXCEPTION "+ e.getMessage());
            e.printStackTrace();
        }
        return msg;
    }

    public static String getErrorMessage3(Response<Text> response) {
        String msg = "";
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            msg = jObjError.getString("text");
        } catch (JSONException | IOException e) {
            Log.e(TAG,"JSON EXCEPTION "+ e.getMessage());
            e.printStackTrace();
        }
        return msg;
    }
}
