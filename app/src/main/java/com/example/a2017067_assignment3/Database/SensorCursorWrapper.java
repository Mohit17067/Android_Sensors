package com.example.a2017067_assignment3.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.a2017067_assignment3.Accelerometer;
import com.example.a2017067_assignment3.Database.SensorDbSchema.*;
import com.example.a2017067_assignment3.GPS;
import com.example.a2017067_assignment3.Wifi;

public class SensorCursorWrapper extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public SensorCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Accelerometer getAccelerometer(){
        String timestamp = getString(getColumnIndex(AccelerometerTable.AccCols.TIME_STAMP));
        String xvalue = getString(getColumnIndex(AccelerometerTable.AccCols.XVALUE));
        String yvalue = getString(getColumnIndex(AccelerometerTable.AccCols.YVALUE));
        String zvalue = getString(getColumnIndex(AccelerometerTable.AccCols.ZVALUE));

        Accelerometer acc = new Accelerometer(timestamp, xvalue, yvalue, zvalue);
        return acc;
    }

    public GPS getGPS(){
        String timestamp = getString(getColumnIndex(GPSTable.GPSCols.TIME_STAMP));
        String longitude = getString(getColumnIndex(GPSTable.GPSCols.LONGITUDE));
        String latitude = getString(getColumnIndex(GPSTable.GPSCols.LATITUDE));

        GPS gps = new GPS(timestamp,latitude,longitude);
        return gps;
    }

    public Wifi getWifi(){
        String timestamp = getString(getColumnIndex(WifiTable.WifiCols.TIME_STAMP));
        String apnames = getString(getColumnIndex(WifiTable.WifiCols.AP_NAMES));
        String apstrengths = getString(getColumnIndex(WifiTable.WifiCols.AP_STRENGTHS));

        Wifi wifi = new Wifi(timestamp, apnames, apstrengths);

        return wifi;
    }
}
