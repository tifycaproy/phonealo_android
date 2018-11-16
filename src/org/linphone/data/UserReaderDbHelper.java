package org.linphone.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by macmini02 on 30/9/16.
 */

public class UserReaderDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "user-bd";

    private static final String TEXT_TYPE       = " TEXT";
    private static final String INTEGER_TYPE    = " INTEGER";
    //private static final String REAL_TYPE       = " REAL";

    private static final String COMMA_SEP = ",";

    public static abstract class UserBalEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_NAME = "NAME";
        public static final String COLUMN_SIP_SERVER = "SIP_SERVER";
        public static final String COLUMN_PORT = "PORT";
        public static final String COLUMN_SECRET = "SECRET";
        public static final String COLUMN_UCOUNTRY_PREFIX = "UCOUNTRY_PREFIX";
        public static final String COLUMN_PHONE = "PHONE_NUMBER";
        public static final String COLUMN_EMAIL = "EMAIL";
        public static final String COLUMN_PIN = "PIN";

        public static final String TABLE_FNAME = "balance";
        public static final String COLUMN_COD = "COD";
        public static final String COLUMN_USU_COD = "USU_COD";
        public static final String COLUMN_CURRENCY = "CURRENCY";
        public static final String COLUMN_AMOUNT = "AMOUNT";

    }

    private static final String SQL_CREATE_USER_ENTRIES =
            "CREATE TABLE " + UserBalEntry.TABLE_NAME + " (" +
                    UserBalEntry._ID        + TEXT_TYPE +"  PRIMARY KEY," +
                    UserBalEntry.COLUMN_ID + TEXT_TYPE + COMMA_SEP +
                    UserBalEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    UserBalEntry.COLUMN_SIP_SERVER + TEXT_TYPE + COMMA_SEP +
                    UserBalEntry.COLUMN_PORT + TEXT_TYPE + COMMA_SEP +
                    UserBalEntry.COLUMN_SECRET + TEXT_TYPE + COMMA_SEP +
                    UserBalEntry.COLUMN_UCOUNTRY_PREFIX + TEXT_TYPE + COMMA_SEP +
                    UserBalEntry.COLUMN_EMAIL + TEXT_TYPE + COMMA_SEP +
                    UserBalEntry.COLUMN_PIN + TEXT_TYPE + COMMA_SEP +
                    UserBalEntry.COLUMN_PHONE + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_BALANCE_ENTRIES =
            "CREATE TABLE " + UserBalEntry.TABLE_FNAME + " (" +
                    UserBalEntry._ID        + TEXT_TYPE +"  PRIMARY KEY," +
                    UserBalEntry.COLUMN_COD + TEXT_TYPE + COMMA_SEP +
                    UserBalEntry.COLUMN_USU_COD + TEXT_TYPE + COMMA_SEP +
                    UserBalEntry.COLUMN_CURRENCY + TEXT_TYPE + COMMA_SEP +
                    UserBalEntry.COLUMN_AMOUNT + TEXT_TYPE +
                    " )";



    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserBalEntry.TABLE_NAME;

    private static final String SQL_DELETE_BAL_ENTRIES =
            "DROP TABLE IF EXISTS " + UserBalEntry.TABLE_FNAME;

    public UserReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_ENTRIES);
        db.execSQL(SQL_CREATE_BALANCE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_BAL_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    //---deletes a particular title---

}
