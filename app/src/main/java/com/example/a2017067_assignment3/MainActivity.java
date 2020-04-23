package com.example.a2017067_assignment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a2017067_assignment3.Database.SensorBaseHelper;
import com.example.a2017067_assignment3.Database.SensorCursorWrapper;
import com.example.a2017067_assignment3.Database.SensorDbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    EditText accelerometer;
    private Context context;
    public static SQLiteDatabase database;
    private Button recordAcc;
    private Button getAcc;
    private Button recordGPS;
    private Button getGPS;
    private Button recordWifi;
    private Button getWifi;
    private Button export;
    private Button share;
    private RecyclerView recyclerView;
    private AccelerometerAdaptor accAdaptor;
    private GPSAdaptor gpsAdaptor;
    private WifiAdaptor wifiAdaptor;
    Intent i;
    static MyReceiver2 myReceiver;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = getApplicationContext();
        this.database = new SensorBaseHelper(context).getWritableDatabase();
        this.recordAcc = (Button) findViewById(R.id.recordAccelerometer);
        this.recordGPS = (Button) findViewById(R.id.recordGPS);
        this.recordWifi = (Button) findViewById(R.id.recordWifi);
        this.getAcc = (Button) findViewById(R.id.getAccelerometer);
        this.getGPS = (Button) findViewById(R.id.getGPS);
        this.getWifi = (Button) findViewById(R.id.getWifi);
        this.export = (Button) findViewById(R.id.export);
        this.share = (Button) findViewById(R.id.share);
        myReceiver = new MyReceiver2();
        recyclerView = (RecyclerView) findViewById(R.id.view_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final IntentFilter intentFilter = new IntentFilter();

        this.recordAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(getApplicationContext(), AccelerometerService.class));

            }
        });

        this.recordGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSService gpsService = new GPSService(getApplicationContext(), MainActivity.this);
                if (!gpsService.canGetLocation()){
                    gpsService.showSettingsAlert();
                }
            }
        });

        this.recordWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myReceiver = new MyReceiver2();
                intentFilter.addAction("BORING2");
                i = new Intent(getApplicationContext(), WifiService.class);
                startService(i);
                registerReceiver(myReceiver, intentFilter);
            }
        });

        this.getWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = database.query(
                        SensorDbSchema.WifiTable.Name,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );

                final SensorCursorWrapper cursor = new SensorCursorWrapper(c);

                List<Wifi> items = new ArrayList<>();

                try {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        items.add(cursor.getWifi());
                        cursor.moveToNext();
                    }
                } finally {
                    cursor.close();
                }
                wifiAdaptor = new WifiAdaptor(items);
                recyclerView.setAdapter(wifiAdaptor);

            }
        });
        this.getAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = database.query(
                        SensorDbSchema.AccelerometerTable.Name,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );

                final SensorCursorWrapper cursor = new SensorCursorWrapper(c);

                List<Accelerometer> items = new ArrayList<>();

                try {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        items.add(cursor.getAccelerometer());
                        cursor.moveToNext();
                    }
                } finally {
                    cursor.close();
                }
                accAdaptor = new AccelerometerAdaptor(items);
                recyclerView.setAdapter(accAdaptor);

            }
        });

        this.getGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = database.query(
                        SensorDbSchema.GPSTable.Name,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );

                final SensorCursorWrapper cursor = new SensorCursorWrapper(c);

                List<GPS> items = new ArrayList<>();

                try {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        items.add(cursor.getGPS());
                        cursor.moveToNext();
                    }
                } finally {
                    cursor.close();
                }
                gpsAdaptor = new GPSAdaptor(items);
                recyclerView.setAdapter(gpsAdaptor);

            }
        });

        this.export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                    new CSVExporter().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                } else {

                    new CSVExporter().execute();
                }
                Toast.makeText(getApplicationContext(), "Data exported to CSV file",
                        Toast.LENGTH_LONG).show();
            }
        });

        this.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                File exportDir = new File(Environment.getExternalStorageDirectory(), "/sensor_data/");
                File accfile = new File(exportDir, "gps.csv");
//                String fileName = "myfile.csv";
//                File sharingGifFile = new File(exportDir, fileName);
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("application/csv");
                Uri uri = Uri.fromFile(accfile);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(shareIntent, "Share CSV"));
            }
        });

    }

    @Override
    protected  void onResume() {
        super.onResume();



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the

                    // contacts-related task you need to do.
                    Log.d("latitude", "hereplz");

                    GPSService gpsService = new GPSService(this,MainActivity.this);
                    if (!gpsService.canGetLocation()){
                        gpsService.showSettingsAlert();
                    }


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    Toast.makeText(this, "You need to grant permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    public class MyReceiver2 extends BroadcastReceiver {
        static final String Log_Tag = "MyReceiver";
        @Override
        public void onReceive(Context arg0, Intent arg1){
            String timestamp = arg1.getStringExtra("timestamps");
            String apnames = arg1.getStringExtra("names");
            String apstrengths = arg1.getStringExtra("str");

            ContentValues val = new ContentValues();
            val.put(SensorDbSchema.WifiTable.WifiCols.TIME_STAMP, timestamp);
            val.put(SensorDbSchema.WifiTable.WifiCols.AP_NAMES, apnames);
            val.put(SensorDbSchema.WifiTable.WifiCols.AP_STRENGTHS, apstrengths);
            database.insert(SensorDbSchema.WifiTable.Name, null, val);
            Log.d("names", apstrengths);
            Toast.makeText(getApplicationContext(), "Wifi Values Recorded",
                    Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), "Service stopped.", Toast.LENGTH_SHORT).show();
            stopService(i);
            unregisterReceiver(myReceiver);
        }

    }

}



