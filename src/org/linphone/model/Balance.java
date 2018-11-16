package org.linphone.model;

import android.content.ContentValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.linphone.data.UserReaderDbHelper;
import org.linphone.logs.Buzlog;

import java.io.Serializable;

/**
 * Created by macmini02 on 3/10/16.
 */

public class Balance implements Serializable {

    private static final String TAG = "Balance";

    private String cod;
    private String usuCod;
    private String currency;
    private String amount;

    public Balance(String cod, String usuCod, String currency, String amount) {
        this.cod = cod;
        this.usuCod = usuCod;
        this.currency = currency;
        this.amount = amount;
    }

    public Balance(JSONObject json) {
        try {
            Buzlog.i(TAG, "User init " + json.toString());

            JSONArray jsonArray = json.getJSONArray("balance");
            JSONObject jsonUser = jsonArray.getJSONObject(0);

            boolean error = false;

            if(!error) {

                if (jsonUser.has("bal_cod"))
                    cod = jsonUser.getString("bal_cod");

                if (jsonUser.has("bal_usu_cod"))
                    usuCod = jsonUser.getString("bal_usu_cod");

                if (jsonUser.has("bal_amount"))
                    amount = jsonUser.getString("bal_amount");

                if (jsonUser.has("bal_currency"))
                    currency = jsonUser.getString("bal_currency");

            }


        }catch (JSONException e){
            Buzlog.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    public ContentValues getContentValues(){
        try {
            ContentValues values = new ContentValues();
            values.put(UserReaderDbHelper.UserBalEntry.COLUMN_COD, getCod());
            values.put(UserReaderDbHelper.UserBalEntry.COLUMN_USU_COD, getUsuCod());
            values.put(UserReaderDbHelper.UserBalEntry.COLUMN_CURRENCY, getCurrency());
            values.put(UserReaderDbHelper.UserBalEntry.COLUMN_AMOUNT, getAmount());

            return values;
        }catch (Exception e){
            Buzlog.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getProjection(){
        String[] projection = {
                UserReaderDbHelper.UserBalEntry.COLUMN_COD,
                UserReaderDbHelper.UserBalEntry.COLUMN_USU_COD,
                UserReaderDbHelper.UserBalEntry.COLUMN_CURRENCY,
                UserReaderDbHelper.UserBalEntry.COLUMN_AMOUNT

        };

        return projection;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getUsuCod() {
        return usuCod;
    }

    public void setUsuCod(String usuCod) {
        this.usuCod = usuCod;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
