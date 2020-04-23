package com.example.a2017067_assignment3;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.a2017067_assignment3.Database.SensorBaseHelper;
import com.example.a2017067_assignment3.Database.SensorDbSchema;

public class GPSService extends Service implements LocationListener {

    boolean canUseGPS = false;
    Activity curActivity;
    Context curContext;

    double latitude;
    double longitude;

    private SQLiteDatabase database;

    public GPSService(Context context, Activity activity) {
        this.curActivity = activity;
        this.curContext = context;
        this.database = new SensorBaseHelper(context).getWritableDatabase();
        useGPS();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public void useGPS() {
        LocationManager locationManager = (LocationManager) this.curContext.getSystemService(LOCATION_SERVICE);

        boolean checkGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (checkGPS) {
            this.canUseGPS = true;
            if (ContextCompat.checkSelfPermission(this.curActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(curActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this.curActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 50);


            }
            else{
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 6000, 1000, this);

                if (locationManager != null) {
                    Location location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        Log.d("latitude", Double.toString(latitude));

                        String lat = Double.toString(latitude);
                        String lon = Double.toString(longitude);
                        Long tsLong = System.currentTimeMillis()/1000;
                        String ts = tsLong.toString();

                        GPS gps = new GPS(ts, lat, lon);
                        ContentValues val = new ContentValues();
                        val.put(SensorDbSchema.GPSTable.GPSCols.TIME_STAMP, gps.getTimestamp());
                        val.put(SensorDbSchema.GPSTable.GPSCols.LATITUDE, gps.getLatitude());
                        val.put(SensorDbSchema.GPSTable.GPSCols.LONGITUDE, gps.getLongitude());
                        database.insert(SensorDbSchema.GPSTable.Name, null, val);
                        Toast.makeText(this.curActivity, "GPS Values Recorded",
                                Toast.LENGTH_LONG).show();

                    }
                }
            }

        }

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.curContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        alertDialog.show();


    }

    public boolean canGetLocation() {
        return this.canUseGPS;
    }
}
