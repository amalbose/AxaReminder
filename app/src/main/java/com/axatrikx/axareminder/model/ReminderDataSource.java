package com.axatrikx.axareminder.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amalbose on 4/4/2015.
 */
public class ReminderDataSource {

    // Database fields
    private SQLiteDatabase database;
    private ReminderDBHelper dbHelper;
    private String[] allColumns = {ReminderDBHelper.COLUMN_ID,
            ReminderDBHelper.COLUMN_NAME,
            ReminderDBHelper.COLUMN_DATE,
            ReminderDBHelper.COLUMN_TIME,
            ReminderDBHelper.COLUMN_RECURRENCE,
            ReminderDBHelper.COLUMN_NOTE,
            ReminderDBHelper.COLUMN_TYPE,
            ReminderDBHelper.COLUMN_ALARMTYPE};

    public ReminderDataSource(Context context) {
        dbHelper = new ReminderDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Reminder createReminder(Reminder reminder) {
        ContentValues values = new ContentValues();
        values.put(ReminderDBHelper.COLUMN_NAME, reminder.getReminderName());
        values.put(ReminderDBHelper.COLUMN_DATE, reminder.getDate());
        values.put(ReminderDBHelper.COLUMN_TIME, reminder.getTime());
        if (reminder.getRecurrence() != null)
            values.put(ReminderDBHelper.COLUMN_RECURRENCE, reminder.getRecurrence());
        if (reminder.getNote() != null)
            values.put(ReminderDBHelper.COLUMN_NOTE, reminder.getNote());
        values.put(ReminderDBHelper.COLUMN_TYPE, reminder.getType());
        values.put(ReminderDBHelper.COLUMN_ALARMTYPE, reminder.getAlarmType());
        long insertId = database.insert(ReminderDBHelper.TABLE_REM, null,
                values);
        System.out.println("Inserted " + insertId);
        Cursor cursor = database.query(ReminderDBHelper.TABLE_REM,
                allColumns, ReminderDBHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Reminder newReminder = cursorToReminder(cursor);
        cursor.close();
        return newReminder;
    }

    public void deleteReminder(long id) {
        database.delete(ReminderDBHelper.TABLE_REM, ReminderDBHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Reminder> getAllReminders() {
        List<Reminder> reminders = new ArrayList<Reminder>();

        Cursor cursor = database.query(ReminderDBHelper.TABLE_REM,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Reminder rem = cursorToReminder(cursor);
            reminders.add(rem);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return reminders;
    }

    private Reminder cursorToReminder(Cursor cursor) {
        Reminder reminder = new Reminder();
        reminder.setId(cursor.getLong(0));
        reminder.setReminderName(cursor.getString(1));
        reminder.setDate(cursor.getString(2));
        reminder.setTime(cursor.getString(3));
        reminder.setRecurrence(cursor.getString(4));
        reminder.setNote(cursor.getString(5));
        reminder.setType(cursor.getString(6));
        reminder.setAlarmType(cursor.getString(7));
        return reminder;
    }
}
