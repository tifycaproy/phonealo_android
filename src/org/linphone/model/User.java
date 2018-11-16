package org.linphone.model;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;
import org.linphone.data.UserReaderDbHelper;
import org.linphone.logs.Buzlog;

import java.io.Serializable;

/**
 * Created by macmini02 on 29/9/16.
 */

public class User implements Serializable{
    private static final String TAG = "User";

    private String id;
    private String name;
    private String sipServer;
    private String pinServer;
    private String port;
    private String secret;
    private String phoneNumber;
    private String user_country_prefix;
    private String email;
    private Balance userBalance;

    public User(String id, String name, String sipServer, String pinServer, String port, String secret, String user_country_prefix, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.sipServer = sipServer;
        this.port = port;
        this.secret = secret;
        this.user_country_prefix = user_country_prefix;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.pinServer = pinServer;
    }

    public  User(JSONObject json) {
        try {
            Buzlog.i(TAG, "User init " + json.toString());

            if(json.has("balance")) {
                this.userBalance = new Balance(json);
            }

            if(json.has("account")) {
                JSONObject jsonUser = json.getJSONObject("account");

                if (jsonUser.has("id")) {
                    id = jsonUser.getString("id");
                    setPinServer(String.valueOf(id));
            }

                if (jsonUser.has("name"))
                    name = jsonUser.getString("name");

                if (jsonUser.has("sipserver"))
                    sipServer = jsonUser.getString("sipserver");

                if (jsonUser.has("port"))
                    port = jsonUser.getString("port");

                if (jsonUser.has("pin"))
                    secret = jsonUser.getString("pin");

            }

        }catch (JSONException e){
            Buzlog.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    public ContentValues getContentValues(){
        try {
            ContentValues values = new ContentValues();
            values.put(UserReaderDbHelper.UserBalEntry.COLUMN_ID, getId());
            values.put(UserReaderDbHelper.UserBalEntry.COLUMN_NAME, getName());
            values.put(UserReaderDbHelper.UserBalEntry.COLUMN_SIP_SERVER, getSipServer());
            values.put(UserReaderDbHelper.UserBalEntry.COLUMN_PORT, getPort());
            values.put(UserReaderDbHelper.UserBalEntry.COLUMN_SECRET, getSecret());
            values.put(UserReaderDbHelper.UserBalEntry.COLUMN_UCOUNTRY_PREFIX, getUser_country_prefix());
            values.put(UserReaderDbHelper.UserBalEntry.COLUMN_PHONE, getPhoneNumber());
            values.put(UserReaderDbHelper.UserBalEntry.COLUMN_EMAIL, getEmail());
            values.put(UserReaderDbHelper.UserBalEntry.COLUMN_PIN, getPinServer());

            return values;
        }catch (Exception e){
            Buzlog.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getProjection(){
        String[] projection = {
                UserReaderDbHelper.UserBalEntry.COLUMN_ID,
                UserReaderDbHelper.UserBalEntry.COLUMN_NAME,
                UserReaderDbHelper.UserBalEntry.COLUMN_SIP_SERVER,
                UserReaderDbHelper.UserBalEntry.COLUMN_PORT,
                UserReaderDbHelper.UserBalEntry.COLUMN_SECRET,
                UserReaderDbHelper.UserBalEntry.COLUMN_UCOUNTRY_PREFIX,
                UserReaderDbHelper.UserBalEntry.COLUMN_PHONE,
                UserReaderDbHelper.UserBalEntry.COLUMN_EMAIL,
                UserReaderDbHelper.UserBalEntry.COLUMN_PIN


        };

        return projection;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSipServer() {
        return sipServer;
    }

    public void setSipServer(String sipServer) {
        this.sipServer = sipServer;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Balance getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(Balance userBalance) {
        this.userBalance = userBalance;
    }

    public String getUser_country_prefix() {
        return user_country_prefix;
    }

    public void setUser_country_prefix(String user_country_prefix) {
        this.user_country_prefix = user_country_prefix;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPinServer() {
        return pinServer;
    }

    private void setPinServer(String pinServer) {

        String pinReverse = new StringBuilder(pinServer).reverse().toString();
        String pin = pinReverse.substring(1, 6);
        this.pinServer = pin;
    }
}
