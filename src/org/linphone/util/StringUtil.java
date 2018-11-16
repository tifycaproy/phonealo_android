package org.linphone.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import org.linphone.logs.Buzlog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by macmini-buzinger02 on 21/08/14.
 */
public class StringUtil {

    public static CharSequence trim(CharSequence s) {
        int start = 0;
        int end   = s.length();
        while (start < end && Character.isWhitespace(s.charAt(start))) {
            start++;
        }

        while (end > start && Character.isWhitespace(s.charAt(end - 1))) {
            end--;
        }

        return s.subSequence(start, end);
    }

    public static boolean isNull(String chars){
        return "".equals(chars) || chars == null || ( chars != null && "".equals(chars.trim()) ) || "null".equalsIgnoreCase(chars);
    }

    public static String toBase64(ImageView img) {
        BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
        Bitmap bmap = drawable.getBitmap();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bb = baos.toByteArray();

        return Base64.encodeToString(bb, Base64.DEFAULT);
    }

    public static String toBase64(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bb = baos.toByteArray();

        return Base64.encodeToString(bb, Base64.DEFAULT);
    }

    public static String computeSHAHash(String text) {
        String SHAHash = null;
        MessageDigest mdSha1;
        try {
            mdSha1 = MessageDigest.getInstance("SHA-1");

            mdSha1.update(text.getBytes("ASCII"));

            byte[] data = mdSha1.digest();
            SHAHash = convertToHex(data);
        } catch (NoSuchAlgorithmException e1) {
            Buzlog.e("Corre", "Mensaje de error al inicializar SHA1");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }

        return SHAHash;
    }

    private static String convertToHex(byte[] data) throws IOException {

        StringBuffer sb = new StringBuffer();
        String hex = null;

        hex = Base64.encodeToString(data, 0, data.length, 0);

        sb.append(hex);

        return sb.toString();
    }

    public static String loadJSONFromAsset(Context context, int id) {
        String json = null;
        if(context != null) {
            try {
                InputStream is = context.getResources().openRawResource(id);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return json;
    }
}
