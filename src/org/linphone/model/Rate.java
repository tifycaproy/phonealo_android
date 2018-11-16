package org.linphone.model;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;
import org.linphone.data.RatesReaderDbHelper;
import org.linphone.logs.Buzlog;

import java.io.Serializable;

/**
 * Created by macmini02 on 30/9/16.
 */

public class Rate implements Serializable {
    private final static String TAG = "Rate";

    private String cod;
    private String country_prefix;
    private String price;
    private String created;
    private String active;
    private String currency;
    private String country_name;

    public Rate(String cod, String country_prefix, String price, String created, String active, String currency,String country_name) {
        this.cod = cod;
        this.country_prefix = country_prefix;
        this.price = price;
        this.created = created;
        this.active = active;
        this.currency = currency;
        this.country_name = country_name;
    }

    public Rate(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {

                if (jsonObject.has("tar_cod"))
                    this.cod = jsonObject.getString("tar_cod");

                if (jsonObject.has("tar_country_prefix"))
                    this.country_prefix = jsonObject.getString("tar_country_prefix");

                if (jsonObject.has("tar_price"))
                    this.price = jsonObject.getString("tar_price");

                if (jsonObject.has("tar_created"))
                    this.created = jsonObject.getString("tar_created");

                if (jsonObject.has("tar_active"))
                    this.active = jsonObject.getString("tar_active");

                if (jsonObject.has("tar_currency"))
                    this.currency = jsonObject.getString("tar_currency");

                if (jsonObject.has("tar_country_name"))
                    //this.country_name = "Japón"; //añadido temporal
                    this.country_name = jsonObject.getString("tar_country_name");
            }

        }catch (JSONException e){
            Buzlog.e(TAG, e.getMessage());
            e.printStackTrace();
        }

    }

    public ContentValues getContentValues(){
        try {
            ContentValues values = new ContentValues();
            values.put(RatesReaderDbHelper.RatesEntry.COLUMN_ID, getCod());
            values.put(RatesReaderDbHelper.RatesEntry.COLUMN_COUNTRY_PREFIX, getCountry_prefix());
            values.put(RatesReaderDbHelper.RatesEntry.COLUMN_PRICE, getPrice());
            values.put(RatesReaderDbHelper.RatesEntry.COLUMN_CREATED, getCreated());
            values.put(RatesReaderDbHelper.RatesEntry.COLUMN_ACTIVE, getActive());
            values.put(RatesReaderDbHelper.RatesEntry.COLUMN_CURRENCY, getCurrency());
            values.put(RatesReaderDbHelper.RatesEntry.COLUMN_COUNTRY_NAME, getCountry_name());

            return values;
        }catch (Exception e){
            Buzlog.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getProjection() {
        String[] projection = {
                RatesReaderDbHelper.RatesEntry.COLUMN_ID,
                RatesReaderDbHelper.RatesEntry.COLUMN_COUNTRY_PREFIX,
                RatesReaderDbHelper.RatesEntry.COLUMN_PRICE,
                RatesReaderDbHelper.RatesEntry.COLUMN_CREATED,
                RatesReaderDbHelper.RatesEntry.COLUMN_ACTIVE,
                RatesReaderDbHelper.RatesEntry.COLUMN_CURRENCY,
                RatesReaderDbHelper.RatesEntry.COLUMN_COUNTRY_NAME
        };

        return projection;

    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getCountry_prefix() {
        return country_prefix;
    }

    public void setCountry_prefix(String country_prefix) {
        this.country_prefix = country_prefix;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountry_name() { return country_name;   }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }
}
