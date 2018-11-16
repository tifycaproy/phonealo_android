package org.linphone.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by macmini02 on 30/9/16.
 */

public class CountriesReaderDbHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "countries-bd";

    private static final String TEXT_TYPE       = " TEXT";
    private static final String INTEGER_TYPE    = " INTEGER";
    //private static final String REAL_TYPE       = " REAL";

    private static final String COMMA_SEP = ",";

    public static abstract class CountriesEntry implements BaseColumns {
        public static final String TABLE_NAME = "countries";
        public static final String COLUMN_COUNTRY_PREFIX = "COUNTRY_PREFIX";
        public static final String COLUMN_COUNTRY_NAME = "COUNTRY_NAME";

    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CountriesReaderDbHelper.CountriesEntry.TABLE_NAME + " (" +
                    CountriesReaderDbHelper.CountriesEntry._ID        + INTEGER_TYPE +"  PRIMARY KEY," +
                    CountriesReaderDbHelper.CountriesEntry.COLUMN_COUNTRY_PREFIX + TEXT_TYPE + COMMA_SEP +
                    CountriesReaderDbHelper.CountriesEntry.COLUMN_COUNTRY_NAME + TEXT_TYPE +
                    " )";



    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CountriesReaderDbHelper.CountriesEntry.TABLE_NAME;

    public CountriesReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    //---deletes a particular title---
}
