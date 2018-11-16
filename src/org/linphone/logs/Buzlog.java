package org.linphone.logs;

import android.util.Log;
import android.widget.Toast;

import org.linphone.application.ApplicationConfig;
import org.linphone.util.StringUtil;


/**
 * Created by FranciscoJavier on 06/05/2014.
 */
public class Buzlog {
    public static boolean logenable = ApplicationConfig.logs;

    public static void i(String tag, String msg) {
        if (logenable && !StringUtil.isNull(msg)) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, boolean toast) {
        if (logenable && !StringUtil.isNull(msg)) {
            Log.i(tag, msg);
            if (toast)
                toast(msg);
        }
    }

    public static void e(String tag, String msg) {
        if (logenable && !StringUtil.isNull(msg))
            Log.e(tag, (msg != null ? msg : ""));
    }

    public static void e(String tag, String msg, Exception e) {
        if (logenable && !StringUtil.isNull(msg)) {
            Log.e(tag, msg, e);
        }
    }

    public static void e(String tag, String msg, boolean toast) {
        if (logenable && !StringUtil.isNull(msg)) {
            Log.e(tag, msg);
            if (toast)
                toast(msg);
        }
    }

    public static void d(String tag, String msg) {
        if (logenable && !StringUtil.isNull(msg))
            Log.d(tag, msg);
    }

    public static void toast(String text) {
        if (logenable && !StringUtil.isNull(text))
            Toast.makeText(ApplicationConfig.getAppContext(), text,
                    Toast.LENGTH_SHORT).show();
    }
}
