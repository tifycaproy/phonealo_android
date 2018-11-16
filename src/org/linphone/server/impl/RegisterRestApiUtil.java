package org.linphone.server.impl;

import android.os.AsyncTask;
import android.os.Build;

import org.json.JSONException;
import org.json.JSONObject;
import org.linphone.application.ApplicationConfig;
import org.linphone.application.RestApiConfig;
import org.linphone.interfaces.CommonInterfaces;
import org.linphone.logs.Buzlog;
import org.linphone.server.ServerUtil;
import org.linphone.server.interfaces.IRegisterRestApiUtil;

import java.io.IOException;
import java.util.HashMap;


/**
 * Created by macmini02 on 27/9/16.
 */

public class RegisterRestApiUtil implements IRegisterRestApiUtil {

    private final static String TAG = "RegisterRestApiUtil";

    // AsyncTask
    AsyncTask<Void, Void, String> mAsyncTask;

    @Override
    public void retrieveData(final String name, final String mobile, final String email, final String prefix, final CommonInterfaces.registerUser in) {
        try{
            mAsyncTask = new AsyncTask<Void, Void, String>() {
                @Override
                protected void onPreExecute() {

                }

                @Override
                protected String doInBackground(Void... params) {
                    return doRetrieveData(name, mobile, email, prefix);

                }

                @Override
                protected void onPostExecute(String result) {
                    in.onGetDataDone(result);
                    mAsyncTask = null;
                }
            };
            mAsyncTask.execute(null, null, null);
        }catch (Exception e){
            Buzlog.e(TAG, "login:" + e.getMessage());
        }

    }

    private String doRetrieveData(String name, String mobile, String email, String prefix) {
        String result = "";

        String endPoint     = RestApiConfig.RA_LOGIN;

        HashMap<String,String> params = new HashMap<>();

        try {

            JSONObject json = new JSONObject();
            json.put("APIKEY", ApplicationConfig.API_KEY);
            json.put("name", name);
            json.put("mobile", mobile);
            json.put("email", email);
            json.put("country_prefix", prefix);
            json.put("deviceinfo", Build.MANUFACTURER + " Android " + Build.VERSION.RELEASE);
            result = ServerUtil.sendRequestToServer(endPoint, ServerUtil.METHOD_POST, json);

        }catch (IOException ioe){
            Buzlog.e(TAG, ioe.getMessage());
        }catch (JSONException e) {
            Buzlog.e(TAG, e.getMessage());
        }

        Buzlog.d(TAG, "JSONResult: " + result);

        return result;
    }
}
