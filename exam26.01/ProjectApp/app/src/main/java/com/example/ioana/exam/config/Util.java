package com.example.ioana.exam.config;

import android.util.Log;

import com.example.ioana.exam.domain.Project;

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

    public static String getErrorMessage(Response<Project> response) {
        String msg = "";
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            msg = jObjError.getString("text");
        } catch (JSONException e) {
            Log.e(TAG,"JSON EXCEPTION "+ e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG,"JSON EXCEPTION "+ e.getMessage());
            e.printStackTrace();
        }
        return msg;
    }


    public static String getErrorMessage2(Response<List<Project>> response) {
        String msg = "";
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            msg = jObjError.getString("text");
        } catch (JSONException e) {
            Log.e(TAG,"JSON EXCEPTION "+ e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG,"JSON EXCEPTION "+ e.getMessage());
            e.printStackTrace();
        }
        return msg;
    }
}
