package org.linphone.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.linphone.application.ApplicationConfig;
import org.linphone.logs.Buzlog;
import org.linphone.model.Balance;
import org.linphone.model.Country;
import org.linphone.model.Rate;
import org.linphone.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macmini02 on 29/9/16.
 */

public class Data {
    private static final String TAG = "Data";

    public static User getUser() {
        User user  = null;

        UserReaderDbHelper mDbHelper = new UserReaderDbHelper(ApplicationConfig.getAppContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] whereValues =  null;
        String where = null;

        String sortOrder = null;

        Cursor c = null;
        try {
            c = db.query(
                    UserReaderDbHelper.UserBalEntry.TABLE_NAME,  // The table to query
                    User.getProjection(),                         // The columns to return
                    where,                                     // The columns for the WHERE clause
                    whereValues,                              // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    sortOrder                                 // The sort order
            );

            if (c != null && c.getCount() > 0) {
                int _id     = c.getColumnIndex(UserReaderDbHelper.UserBalEntry._ID);
                int id      = c.getColumnIndex(UserReaderDbHelper.UserBalEntry.COLUMN_ID);
                int name    = c.getColumnIndex(UserReaderDbHelper.UserBalEntry.COLUMN_NAME);
                int sipserver   = c.getColumnIndex(UserReaderDbHelper.UserBalEntry.COLUMN_SIP_SERVER);
                int port  = c.getColumnIndex(UserReaderDbHelper.UserBalEntry.COLUMN_PORT);
                int secret  = c.getColumnIndex(UserReaderDbHelper.UserBalEntry.COLUMN_SECRET);
                int cPrefix  = c.getColumnIndex(UserReaderDbHelper.UserBalEntry.COLUMN_UCOUNTRY_PREFIX);
                int phone  = c.getColumnIndex(UserReaderDbHelper.UserBalEntry.COLUMN_PHONE);
                int email = c.getColumnIndex(UserReaderDbHelper.UserBalEntry.COLUMN_EMAIL);
                int pin = c.getColumnIndex(UserReaderDbHelper.UserBalEntry.COLUMN_PIN);


                if (c != null && c.moveToNext()) {
                    user = new User(
                            c.getString(id),
                            c.getString(name),
                            c.getString(sipserver),
                            c.getString(pin),
                            c.getString(port),
                            c.getString(secret),
                            c.getString(cPrefix),
                            c.getString(phone),
                            c.getString(email)
                    );


                }

            }
        }catch (Exception e){
            Buzlog.e(TAG, e.getMessage());
            e.printStackTrace();
        }finally {
            if( c != null) c.close();
            mDbHelper.close();
            db.close();
            db = null;
        }

        return user;
    }

    public static void upsert(User mUser){
        UserReaderDbHelper mDbHelper = new UserReaderDbHelper(ApplicationConfig.getAppContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        Cursor c = null;
        try {
            String[] whereValues,whereBalanceValues;
            String where,whereBalance;

            where = UserReaderDbHelper.UserBalEntry.COLUMN_ID + " = ?  ";

            whereBalance = UserReaderDbHelper.UserBalEntry.COLUMN_COD + " = ? ";


            whereValues = new String[]{String.valueOf(mUser.getId())};

            whereBalanceValues = new String[]{String.valueOf(mUser.getUserBalance().getCod())};

            c = db.query(
                    UserReaderDbHelper.UserBalEntry.TABLE_NAME,  // The table to query
                    mUser.getProjection(),                         // The columns to return
                    where,                                     // The columns for the WHERE clause
                    whereValues,                              // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                // The sort order
            );

            if (c != null && c.getCount() > 0) {
                db.update(UserReaderDbHelper.UserBalEntry.TABLE_NAME, mUser.getContentValues(), where, whereValues);
                db.update(UserReaderDbHelper.UserBalEntry.TABLE_FNAME, mUser.getUserBalance().getContentValues(), whereBalance, whereBalanceValues);
            } else {
                db.insert(UserReaderDbHelper.UserBalEntry.TABLE_NAME, null, mUser.getContentValues());
                db.insert(UserReaderDbHelper.UserBalEntry.TABLE_FNAME, null, mUser.getUserBalance().getContentValues());
            }

        } finally {
            if (c != null) c.close();
            db.close();
            db = null;
        }




        /*try {
            db.insert(UserReaderDbHelper.UserBalEntry.TABLE_NAME, null, mUser.getContentValues());
            db.insert(UserReaderDbHelper.UserBalEntry.TABLE_FNAME, null, mUser.getUserBalance().getContentValues());

        } finally {
            db.close();
            db = null;
        }*/
    }//fin upsert

    public static void deleteUser() {
        UserReaderDbHelper mDbHelper = new UserReaderDbHelper(ApplicationConfig.getAppContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String selection = null;
        String[] args = null;

        db.delete(UserReaderDbHelper.UserBalEntry.TABLE_NAME,
                selection,
                args);

        db.close();
        db = null;

    }

    public static void  upsert(Country mCountry) {
        CountriesReaderDbHelper mDbHelper = new CountriesReaderDbHelper(ApplicationConfig.getAppContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        Cursor c = null;
        try {
            String[] whereValues;
            String where;

            where = CountriesReaderDbHelper.CountriesEntry.COLUMN_COUNTRY_PREFIX + " = ?  ";
            whereValues = new String[]{String.valueOf(mCountry.getCountry_prefix())};

            c = db.query(
                    CountriesReaderDbHelper.CountriesEntry.TABLE_NAME,  // The table to query
                    mCountry.getProjection(),                         // The columns to return
                    where,                                     // The columns for the WHERE clause
                    whereValues,                              // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                // The sort order
            );

            if (c != null && c.getCount() > 0) {

                db.update(CountriesReaderDbHelper.CountriesEntry.TABLE_NAME, mCountry.getContentValues(), where, whereValues);
            } else {
                db.insert(CountriesReaderDbHelper.CountriesEntry.TABLE_NAME, null, mCountry.getContentValues());
            }

        } finally {
            if (c != null) c.close();
            db.close();
            db = null;
        }
    }

    public static Balance getUserBalance() {
        Balance mBalance = null;

        UserReaderDbHelper mDbHelper = new UserReaderDbHelper(ApplicationConfig.getAppContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] whereValues = null;
        String where = null;

        String sortOrder =  null;

        Cursor c = null;
        try {
            c = db.query(
                    UserReaderDbHelper.UserBalEntry.TABLE_FNAME,  // The table to query
                    Balance.getProjection(),                         // The columns to return
                    where,                                     // The columns for the WHERE clause
                    whereValues,                              // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    sortOrder                                 // The sort order
            );

            if (c != null && c.getCount() > 0) {
                int _id     = c.getColumnIndex(UserReaderDbHelper.UserBalEntry._ID);
                int cod  = c.getColumnIndex(UserReaderDbHelper.UserBalEntry.COLUMN_COD);
                int usu_cod    = c.getColumnIndex(UserReaderDbHelper.UserBalEntry.COLUMN_USU_COD);
                int currency    = c.getColumnIndex(UserReaderDbHelper.UserBalEntry.COLUMN_CURRENCY);
                int amount    = c.getColumnIndex(UserReaderDbHelper.UserBalEntry.COLUMN_AMOUNT);


                while (c != null && c.moveToNext()) {
                    mBalance = new Balance(
                            c.getString(cod),
                            c.getString(usu_cod),
                            c.getString(currency),
                            c.getString(amount)
                    );
                }

            }
        }catch (Exception e){
            Buzlog.e(TAG, e.getMessage());
            e.printStackTrace();
        }finally {
            if( c != null) c.close();
            mDbHelper.close();
            db.close();
            db = null;
        }

        return mBalance;
    }

    public static List<Country> getAllCountries(String user_prefix){
        ArrayList<Country> countries = new ArrayList<>();

        CountriesReaderDbHelper mDbHelper = new CountriesReaderDbHelper(ApplicationConfig.getAppContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] whereValues = null;
        String where = null;

        if(user_prefix != null && !user_prefix.isEmpty()) {
            where = CountriesReaderDbHelper.CountriesEntry.COLUMN_COUNTRY_PREFIX + " = ?  ";
            whereValues = new String[]{String.valueOf(user_prefix)};
        }

        String sortOrder =  null;

        Cursor c = null;
        try {
            c = db.query(
                    CountriesReaderDbHelper.CountriesEntry.TABLE_NAME,  // The table to query
                    Country.getProjection(),                         // The columns to return
                    where,                                     // The columns for the WHERE clause
                    whereValues,                              // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    sortOrder                                 // The sort order
            );

            if (c != null && c.getCount() > 0) {
                int _id     = c.getColumnIndex(CountriesReaderDbHelper.CountriesEntry._ID);
                int prefix  = c.getColumnIndex(CountriesReaderDbHelper.CountriesEntry.COLUMN_COUNTRY_PREFIX);
                int name    = c.getColumnIndex(CountriesReaderDbHelper.CountriesEntry.COLUMN_COUNTRY_NAME);


                while (c != null && c.moveToNext()) {
                    countries.add(new Country(
                            c.getLong(_id),
                            c.getString(prefix),
                            c.getString(name)

                    ));
                }

            }
        }catch (Exception e){
            Buzlog.e(TAG, e.getMessage());
            e.printStackTrace();
        }finally {
            if( c != null) c.close();
            mDbHelper.close();
            db.close();
            db = null;
        }

        return countries;
    }

    public static void  upsert(Rate mRate) {
        RatesReaderDbHelper mDbHelper = new RatesReaderDbHelper(ApplicationConfig.getAppContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        Cursor c = null;
        try {
            String[] whereValues;
            String where;

            where = RatesReaderDbHelper.RatesEntry.COLUMN_ID+ " = ?  ";
            whereValues = new String[]{String.valueOf(mRate.getCod())};

            c = db.query(
                    RatesReaderDbHelper.RatesEntry.TABLE_NAME,  // The table to query
                    mRate.getProjection(),                         // The columns to return
                    where,                                     // The columns for the WHERE clause
                    whereValues,                              // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                // The sort order
            );

            if (c != null && c.getCount() > 0) {

                db.update(RatesReaderDbHelper.RatesEntry.TABLE_NAME, mRate.getContentValues(), where, whereValues);
            } else {
                db.insert(RatesReaderDbHelper.RatesEntry.TABLE_NAME, null, mRate.getContentValues());
            }

        } finally {
            if (c != null) c.close();
            db.close();
            db = null;
        }
    }

    public static List<Rate> getAllRates() {
        ArrayList<Rate> rates = new ArrayList<>();

        RatesReaderDbHelper mDbHelper = new RatesReaderDbHelper(ApplicationConfig.getAppContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] whereValues = null;
        String where = null;

        String sortOrder =  null;

        Cursor c = null;
        try {
            c = db.query(
                    RatesReaderDbHelper.RatesEntry.TABLE_NAME,  // The table to query
                    Rate.getProjection(),                         // The columns to return
                    where,                                     // The columns for the WHERE clause
                    whereValues,                              // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    sortOrder                                 // The sort order
            );

            if (c != null && c.getCount() > 0) {
                int id     = c.getColumnIndex(RatesReaderDbHelper.RatesEntry.COLUMN_ID);
                int prefix  = c.getColumnIndex(RatesReaderDbHelper.RatesEntry.COLUMN_COUNTRY_PREFIX);
                int price  = c.getColumnIndex(RatesReaderDbHelper.RatesEntry.COLUMN_PRICE);
                int created  = c.getColumnIndex(RatesReaderDbHelper.RatesEntry.COLUMN_CREATED);
                int active  = c.getColumnIndex(RatesReaderDbHelper.RatesEntry.COLUMN_ACTIVE);
                int currency  = c.getColumnIndex(RatesReaderDbHelper.RatesEntry.COLUMN_CURRENCY);
                int country_name  = c.getColumnIndex(RatesReaderDbHelper.RatesEntry.COLUMN_COUNTRY_NAME);


                while (c != null && c.moveToNext()) {
                    rates.add(new Rate(
                            c.getString(id),
                            c.getString(prefix),
                            c.getString(price),
                            c.getString(created),
                            c.getString(active),
                            c.getString(currency),
                            c.getString(country_name)
                    ));
                }

            }
        }catch (Exception e){
            Buzlog.e(TAG, e.getMessage());
            e.printStackTrace();
        }finally {
            if( c != null) c.close();
            mDbHelper.close();
            db.close();
            db = null;
        }

        return rates;
    }


}
