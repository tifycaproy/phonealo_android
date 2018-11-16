package org.linphone.server.impl;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.linphone.application.RestApiConfig;
import org.linphone.interfaces.CommonInterfaces;
import org.linphone.logs.Buzlog;
import org.linphone.server.ServerUtil;
import org.linphone.server.interfaces.ICountryRestApiUtil;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by macmini02 on 29/9/16.
 */

public class CountryRestApiUtil implements ICountryRestApiUtil {

    private final static String TAG = "CountryRestApiUtil";

    // AsyncTask
    AsyncTask<Void, Void, String> mAsyncTask;


    @Override
    public void getCountries(final CommonInterfaces.Country in) {
        try{
            mAsyncTask = new AsyncTask<Void, Void, String>() {
                @Override
                protected void onPreExecute() {

                }

                @Override
                protected String doInBackground(Void... params) {
                    return doGetCountries();

                }

                @Override
                protected void onPostExecute(String result) {
                    in.onGetCountriesDone(result);

                    mAsyncTask = null;
                }
            };
            mAsyncTask.execute(null, null, null);
        }catch (Exception e){
            Buzlog.e(TAG, "login:" + e.getMessage());
        }
    }

    private String doGetCountries() {
        String result = "";

        String endPoint     = RestApiConfig.RA_COUNTRY;

        HashMap<String,String> params = new HashMap<>();

        try {

            JSONObject json = new JSONObject();
            json.put("APIKEY", "da39a3ee5e6b4b0d3255bfef95601890afd80709");
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
