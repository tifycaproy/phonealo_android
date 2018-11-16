package org.linphone.model;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;
import org.linphone.data.CountriesReaderDbHelper;
import org.linphone.logs.Buzlog;

import java.io.Serializable;

/**
 * Created by macmini02 on 29/9/16.
 */

public class Country implements Serializable {
    private static final String TAG = "Country";

    private long id;
    private String country_prefix;
    private String country_name;

    public Country(long id, String country_prefix, String country_name) {
        this.id = id;
        this.country_prefix = country_prefix;
        this.country_name = country_name;
    }

    public Country(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {

                if (jsonObject.has("country_prefix"))
                    this.country_prefix = jsonObject.getString("country_prefix");

                if (jsonObject.has("country_name"))
                    this.country_name = jsonObject.getString("country_name");
            }

        }catch (JSONException e){
            Buzlog.e(TAG, e.getMessage());
            e.printStackTrace();
        }

    }

    public ContentValues getContentValues(){
        try {
            ContentValues values = new ContentValues();
            values.put(CountriesReaderDbHelper.CountriesEntry.COLUMN_COUNTRY_PREFIX, getCountry_prefix());
            values.put(CountriesReaderDbHelper.CountriesEntry.COLUMN_COUNTRY_NAME, getCountry_name());

            return values;
        }catch (Exception e){
            Buzlog.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getProjection(){
        String[] projection = {
                CountriesReaderDbHelper.CountriesEntry._ID,
                CountriesReaderDbHelper.CountriesEntry.COLUMN_COUNTRY_PREFIX,
                CountriesReaderDbHelper.CountriesEntry.COLUMN_COUNTRY_NAME

        };

        return projection;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_prefix() {
        return country_prefix;
    }

    public void setCountry_prefix(String country_prefix) {
        this.country_prefix = country_prefix;
    }

}
