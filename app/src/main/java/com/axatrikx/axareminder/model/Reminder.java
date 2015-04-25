package com.axatrikx.axareminder.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amalbose on 3/21/2015.
 */
public class Reminder implements Parcelable {

    private long id;
    private String reminderName;
    private String date;
    private String time;
    private String recurrence = null;
    private String note = null;
    private String type;
    private String alarmType;
    private String createdTime;

    public Reminder(){

    }

    public String getReminderName() {
        return reminderName;
    }

    public void setReminderName(String reminderName) {
        this.reminderName = reminderName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }


    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String toString() {
        return id + "," + reminderName + "," + date + "," + time + "," + recurrence + "," + note + "," + type + "," + alarmType;
    }


    protected Reminder(Parcel in) {
        id = in.readLong();
        reminderName = in.readString();
        date = in.readString();
        time = in.readString();
        recurrence = in.readString();
        note = in.readString();
        type = in.readString();
        alarmType = in.readString();
        createdTime = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(reminderName);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(recurrence);
        dest.writeString(note);
        dest.writeString(type);
        dest.writeString(alarmType);
        dest.writeString(createdTime);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Reminder> CREATOR = new Parcelable.Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

}