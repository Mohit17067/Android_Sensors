package com.example.a2017067_assignment3;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;


import androidx.annotation.Nullable;

import com.example.a2017067_assignment3.Database.SensorBaseHelper;
import com.example.a2017067_assignment3.Database.SensorDbSchema;

import  com.example.a2017067_assignment3.Database.SensorDbSchema.*;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class AccelerometerService extends IntentService implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor macc;
    private Context context;
    private SQLiteDatabase database;

    public AccelerometerService() {
        super("AccelerometerService");
    }


    @Override
    public void onCreate(){
        super.onCreate();
        this.context = getApplicationContext();
        this.database = new SensorBaseHelper(context).getWritableDatabase();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        macc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager.registerListener(this, macc, SensorManager.SENSOR_DELAY_NORMAL);
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        String timestamp = Double.toString(event.timestamp/1000000000.0);
        String x_value = Float.toString(values[0]);
        String y_value = Float.toString(values[1]);
        String z_value = Float.toString(values[2]);

        Accelerometer acc = new Accelerometer(timestamp, x_value, y_value, z_value);
        ContentValues val = new ContentValues();
        val.put(AccelerometerTable.AccCols.TIME_STAMP, acc.getTimestamp());
        val.put(AccelerometerTable.AccCols.XVALUE, acc.getXvalue());
        val.put(AccelerometerTable.AccCols.YVALUE, acc.getYvalue());
        val.put(AccelerometerTable.AccCols.ZVALUE, acc.getZvalue());
        database.insert(SensorDbSchema.AccelerometerTable.Name, null, val);
        Toast.makeText(this.context, "Accelerometer Values Recorded",
                Toast.LENGTH_LONG).show();
        sensorManager.unregisterListener(this);
        stopSelf();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
