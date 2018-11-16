package org.linphone.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import org.linphone.data.Data;
import org.linphone.logs.Buzlog;
import org.linphone.model.User;

import io.fabric.sdk.android.Fabric;


/**
 * Created by fjdelahorra on 30/3/16.
 */
public class ApplicationConfig extends Application{

    private static final String TAG = "ApplicationConfig";
    private static Context mContext;

    public final static String DESARROLLO = "DESA";
    public final static String PRODUCTION = "PROD";
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String RANKING = "RANKINGS";
    public static final String GROUPS = "GROUPS";

    public  static String enviroment = ApplicationConfig.DESARROLLO;
    public final static boolean logs = !ApplicationConfig.isRunningOnProduction();

    private static final String SERVER_URL_DESA = "http://app.phonealo.net/api/";
    private static final String SERVER_URL_DESA = "http://app.phonealo.net";
    private static final String SERVER_URL_PROD = "http://app.phonealo.net";
    private static final String SERVER_URL_CONTEXT = "/v2";

    public static final String API_KEY = "bbfe5fbe0412e10fe8d4b1c13f50eb5848e25a8b";

    //private static final String SERVER_URL_DESA = "http://devapp.phonealo.net";
    //private static final String SERVER_URL_PROD = "http://devapp.phonealo.net";
    //private static final String SERVER_URL_CONTEXT = "/v2";

    //public static final String API_KEY = "778bf56e26defc62cb1c93f05cfa41f32a7b8cbe";

    public static int REMINDER_CALENDAR_MINUTES = 60;

    private static User mUser;

    public static String currency_char;

    public final static boolean certificate = false;

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationConfig.mContext = getApplicationContext();

        Fabric.with(this, new Crashlytics());
    }


    public static User getCurrentUser(){
        if(mUser == null) {
            mUser = Data.getUser();
        }

        return mUser;
    }


    public static boolean deleteCurrentUser(){
        mUser = null;
        Data.deleteUser();
        return true;
    }


    public static Context getAppContext() {
        return ApplicationConfig.mContext;
    }

    public static boolean isRunningOnProduction() {
        return enviroment.equals(ApplicationConfig.PRODUCTION);
    }

    public static String getServerUrl() {
        return ApplicationConfig.enviroment.equals(ApplicationConfig.DESARROLLO) ? SERVER_URL_DESA : SERVER_URL_PROD;
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){
            Buzlog.e(TAG, e.getMessage());
        }
    }

/*
    public static User getCurrentUser(){
        if(mUser == null) {
            mUser = Data.getUsuarioActive();
        }

        return mUser;
    }
*/

    public static void setupUI(View view, final Activity activity) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, activity);
            }
        }
    }


    public static boolean validateEditOpcional(TextView editText, String mensajeError, int min, int max) {
        boolean valido = true;

        if (editText.getText() == null || editText.length() == 0 || editText.getText().toString().trim().equals("")) {

            return valido;
        }


        return validateEdit(editText,mensajeError,min,max);

    }

    public static boolean validateEdit(TextView editText, String mensajeError, int min, int max) {
        boolean valido = true;

        if (editText.getText() == null || editText.length() == 0 || editText.getText().toString().trim().equals("")) {
            valido = false;
        }else if(editText.getText() != null && (editText.getText().length() < min || editText.getText().length() > max)){
            valido = false;
        } else {
            editText.setError(null);
        }

        if(!valido){
            if(editText.getParent() != null && editText.getParent() instanceof TextView){
                ((TextView) editText.getParent()).setError(mensajeError);
            }else {
                editText.setError(mensajeError);
            }

            editText.requestFocus();
        }else{
                editText.setError(null);
        }

        return valido;

    }

    public static boolean validateEdit(TextView editText, String mensajeError) {
        boolean valido = true;
        if (editText.getText() == null || editText.length() == 0
                || editText.getText().toString().trim().equals("")) {


            editText.setError(mensajeError);
            editText.requestFocus();
            valido = false;
        } else {
            editText.setError(null);
        }

        return valido;

    }

    public static boolean validateEmail(EditText editText, String mensajeError) {
        boolean valido = true;
        if (editText.getText() == null
                || !editText.getText().toString()
                .matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")
                || editText.length() == 0) {


            editText.setError(mensajeError);

            editText.requestFocus();
            valido = false;
        } else {
            editText.setError(null);
        }

        return valido;
    }



    public static boolean validatePhone(EditText editText, String mensajeError){
        boolean valido = true;


        if("".equalsIgnoreCase(editText.getText().toString())){
            editText.setError(mensajeError);
            valido = false;
        }else  if(editText.getText() == null || (editText.length() > 0 && !editText.getText().toString().matches("^([0-9]{6,14})"))){
            editText.setError(mensajeError);
            valido = false;
        }else{
            editText.setError(null);
        }

        return valido;

    }

    public static String getStringProperty(String property){
        SharedPreferences settings = getAppContext().getSharedPreferences(PREFS_NAME, 0);
        return settings.getString(property, "");
    }

    public static void setStringProperty(String property, String value) {
        SharedPreferences sharedPref = getAppContext().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(property, value);
        editor.commit();
    }

    public static boolean getBooleanProperty(String property){
        SharedPreferences settings = getAppContext().getSharedPreferences(PREFS_NAME, 0);
        return settings.getBoolean(property, false);
    }

    public static void setBooleanProperty(String property, boolean value){
        SharedPreferences sharedPref = getAppContext().getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(property, value);
        editor.commit();
    }

    public static String getAndroidId() {
        return Settings.Secure.getString(getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static int getSDKVersion(){
        return Build.VERSION.SDK_INT;
    }
    public static boolean allowAnimation(){ return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP; }




}
