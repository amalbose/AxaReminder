package com.axatrikx.axareminder.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

/**
 * Created by amalbose on 4/4/2015.
 */
public class ReminderDBHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "axareminder.db";
    private static final int DATABASE_VERSION = 1;

    // Database table
    public static final String TABLE_REM = "axareminder";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_RECURRENCE = "recurrence";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_ALARMTYPE = "alarmtype";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_REM
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_DATE + " text not null,"
            + COLUMN_TIME + " text not null,"
            + COLUMN_RECURRENCE + " text,"
            + COLUMN_NOTE + " text,"
            + COLUMN_TYPE + " text not null,"
            + COLUMN_ALARMTYPE + " text not null"
            + ");";

    public ReminderDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.out.println(Environment.getDataDirectory());
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REM);
        onCreate(db);
    }


}
