package org.linphone.data;


import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.linphone.R;
import org.linphone.logs.Buzlog;
import org.linphone.model.Country;
import org.linphone.model.Rate;
import org.linphone.util.StringUtil;

/**
 * Created by macmini-buzinger02 on 21/10/15.
 */
public class DataSynchronized {
    public static final String TAG = "DataSynchronized";

    public static void insertUpdateCountries(Context context, String result) {
        //result = StringUtil.loadJSONFromAsset(context, R.raw.setup); //	EJEMPLO, quitar más adelante

        JSONArray array;

        try {

            JSONObject jsonObject= new JSONObject(result);
            if (jsonObject.has("countries")) {
                array = jsonObject.getJSONArray("countries");

                for (int i = 0; i < array.length(); i++) {
                    Country mCountry = new Country(array.getJSONObject(i));
                    Data.upsert(mCountry);
                }

            } else {
                Buzlog.d(TAG, "No se ha podido encontrar el objeto \"countries\"");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void insertUpdateRates(Context context, String result) {
        //result = StringUtil.loadJSONFromAsset(context, R.raw.setup); //	EJEMPLO, quitar más adelante

        JSONArray array;

        try {
            JSONObject jsonObject= new JSONObject(result);


            if (jsonObject.has("rates")) {
                array = jsonObject.getJSONArray("rates");

                for (int i = 0; i < array.length(); i++) {
                    Rate mRate = new Rate(array.getJSONObject(i));
                    Data.upsert(mRate);
                }

            } else {
                Buzlog.d(TAG, "No se ha podido encontrar el objeto \"countries\"");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
