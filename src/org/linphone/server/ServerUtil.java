package org.linphone.server;


import org.linphone.application.RestApiConfig;
import org.linphone.interfaces.CommonInterfaces;
import org.linphone.logs.Buzlog;
import org.linphone.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by FranciscoJavier on 23/06/2014.
 */
public class ServerUtil {
    private static String TAG = "ServerUtil";
    private static String ARGS_USER_AGENT = "android";
    private static String ARGS_STATUS_FILTER = "statusFilter";

    public final static String METHOD_GET = "GET";
    public final static String METHOD_POST = "POST";
    public final static String METHOD_PUT = "PUT";
    public final static String METHOD_DELETE = "DELETE";

    private final static String CONTENT_TYPE_FORM_URL_ENCODED   = "application/x-www-form-urlencoded;charset=UTF-8";
    private final static String CONTENT_TYPE_JSON               = "application/json;charset=UTF-8";

    public final static String ERROR_PARAMS = "EP";



    /**
     * * Send Request to server with url params object
     * @param endpoint
     * @param method
     * @param params
     * @return
     * @throws IOException
     */
    public static String sendRequestToServer(String endpoint, String method, Map<String, String> params) throws IOException {
        Buzlog.i(TAG, "sendRequestToServer " + ":: url ::" + endpoint + ":: params :: " + params.toString());

        if(StringUtil.isNull(endpoint) || StringUtil.isNull(method) ) return ERROR_PARAMS;

        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator    = params.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry<String, String> param = iterator.next();
            if(!StringUtil.isNull(param.getKey()) && !StringUtil.isNull(param.getValue())){
                bodyBuilder.append(param.getKey()).append('=').append(URLEncoder.encode(param.getValue(), "UTF-8"));
                if (iterator.hasNext())
                    bodyBuilder.append('&');
            }
        }
        byte[] mBytes = bodyBuilder.toString().getBytes();
        iterator = null;

        if(METHOD_GET.equals(method)){
            mBytes   = null;
            if(bodyBuilder != null && bodyBuilder.length() > 0) {
                endpoint = endpoint.concat("?");
                endpoint = endpoint.concat(bodyBuilder.toString());
            }
        }

        return sendRequestToServer(endpoint,method,mBytes, CONTENT_TYPE_FORM_URL_ENCODED);
    }

    /**
     * Send Request to server with json object
     * @param endpoint
     * @param method
     * @param json
     * @return
     * @throws IOException
     */
    public static String sendRequestToServer(String endpoint, String method, org.json.JSONObject json) throws IOException {
        Buzlog.i(TAG, "sendRequestToServer " + ":: url ::" + endpoint + ":: params :: " + json.toString());

        if(StringUtil.isNull(endpoint) || StringUtil.isNull(method) || StringUtil.isNull(json.toString())) return ERROR_PARAMS;

        return sendRequestToServer(endpoint,method,json.toString().getBytes(),CONTENT_TYPE_JSON);
    }


    public static String sendRequestToServer(String endpoint) throws  IOException {
        return sendRequestToServer(endpoint, METHOD_GET, null, CONTENT_TYPE_FORM_URL_ENCODED);

    }

    public static String sendRequestToServer(String endpoint, String method) throws  IOException{
        return sendRequestToServer(endpoint,method,null,CONTENT_TYPE_JSON);

    }
    /**
     *
     * @param endpoint
     * @param method
     * @param bytes
     * @return
     * @throws IOException
     */
    public  static String sendRequestToServer(String endpoint,String method,  byte[] bytes, String contentType)  throws IOException {
        Buzlog.d(TAG, "sendRequestToServer");

        StringBuffer respuesta = new StringBuffer();
        URL url;

        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }

        OutputStream out;
        HttpURLConnection conn = null;
        HttpsURLConnection connHttps = null;
        try {


            conn = (HttpURLConnection) url.openConnection();
            if(method.equalsIgnoreCase(METHOD_POST)) conn.setDoOutput(true);
            conn.setUseCaches(false);
            if(bytes != null) conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", contentType);
            conn.setRequestProperty("user-Agend", ARGS_USER_AGENT);


            if(bytes != null) {
                out = conn.getOutputStream();
                out.write(bytes);
                out.close();
                out = null;
            }

            // handle the response

            if (conn.getResponseCode() < 200 || conn.getResponseCode() > 205 ) {
                Buzlog.e(TAG, "request failed with error code " + conn.getResponseCode() + ":: url ::" + url.toString() );
                respuesta.append(conn.getResponseCode());
            }else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String linea;
                while ((linea = in.readLine()) != null) {
                    respuesta.append(linea);
                }
                in.close();
                in = null;
                System.gc();
            }


            return respuesta.toString();
        }catch (ConnectException e) {
            Buzlog.e(TAG,"post::" +e.getCause() );
            e.printStackTrace();
            return RestApiConfig.NOT_FOUND;
        }


        catch (IOException ioe) {
            Buzlog.e(TAG, "post::" + ioe.getCause());
            ioe.printStackTrace();
            if(conn.getResponseCode() == 401){
                return RestApiConfig.UNATHORIZE;

            }else{
                return null;

            }

        }catch (Exception e){
            Buzlog.e(TAG, "post::" + e.getCause());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if(connHttps != null){
                connHttps.disconnect();
            }
        }
        return null;
    }

    public static boolean proccessResult(String result, int callRef,  CommonInterfaces.Exceptions in){

        if(result == null){

            in.ConectionException(callRef);
            return Boolean.TRUE;

        }else if(result.equals(RestApiConfig.NOT_FOUND)){

            in.NotFound(callRef);
            return Boolean.TRUE;

        }else if(result.equals(RestApiConfig.UNATHORIZE)){

            in.onUnauthorized(callRef);
            return Boolean.TRUE;

        }else if(result.equals(RestApiConfig.FORBIDEN)) {

            in.onForbbiden(callRef);
            return Boolean.TRUE;

        }else if(result.equals(RestApiConfig.ERROR_SERVER)) {

            in.ErrorServer(callRef);
            return Boolean.TRUE;

        }

        return Boolean.FALSE;
    }

}
