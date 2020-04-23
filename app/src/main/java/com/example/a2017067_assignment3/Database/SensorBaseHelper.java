package com.example.a2017067_assignment3.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SensorBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sensorBase.db";

    public SensorBaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + SensorDbSchema.AccelerometerTable.Name + "("+
                SensorDbSchema.AccelerometerTable.AccCols.TIME_STAMP + "," +
                SensorDbSchema.AccelerometerTable.AccCols.XVALUE + ","+
                SensorDbSchema.AccelerometerTable.AccCols.YVALUE + "," +
                SensorDbSchema.AccelerometerTable.AccCols.ZVALUE +
                ")"
        );

        db.execSQL("create table " + SensorDbSchema.GPSTable.Name + "(" +
                SensorDbSchema.GPSTable.GPSCols.TIME_STAMP + "," +
                SensorDbSchema.GPSTable.GPSCols.LATITUDE + "," +
                SensorDbSchema.GPSTable.GPSCols.LONGITUDE +
                ")"
        );

        db.execSQL("create table " + SensorDbSchema.WifiTable.Name + "(" +
                SensorDbSchema.WifiTable.WifiCols.TIME_STAMP + "," +
                SensorDbSchema.WifiTable.WifiCols.AP_NAMES + "," +
                SensorDbSchema.WifiTable.WifiCols.AP_STRENGTHS +
                ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
