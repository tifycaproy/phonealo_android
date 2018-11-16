package org.linphone.application;

/**
 * Created by macmini-buzinger02 on 01/12/15.
 */
public class RestApiConfig {
    public  static final String OK_RESPONSE = "200";
    public  static final String NOT_FOUND = "404";
    public  static final String UNATHORIZE = "401";
    public  static final String FORBIDEN = "403";
    public  static final String NOT_EXIST = "400";
    public  static final String ERROR_SERVER = "500";
    public  static final String EMAIL_EXIST = "202";

    //  LOGIN
    public final static String RA_LOGIN = ApplicationConfig.getServerUrl().concat("/api/userRegister");

    //  COUNTRIES
    public final static String RA_COUNTRY = ApplicationConfig.getServerUrl().concat("/api/setup");

    //  SALDO
    public final static String RA_BALANCE = ApplicationConfig.getServerUrl().concat("/api/balanceGet");

}
