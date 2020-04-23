package com.example.a2017067_assignment3;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.a2017067_assignment3.Database.SensorBaseHelper;
import com.example.a2017067_assignment3.Database.SensorCursorWrapper;
import com.example.a2017067_assignment3.Database.SensorDbSchema;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



public class CSVExporter extends AsyncTask<String, Void, Boolean> {




    protected Boolean doInBackground(final String... args) {
        Log.d("latitude", "start");
        File exportDir = new File(Environment.getExternalStorageDirectory(), "/sensor_data/");
        if (!exportDir.exists()) { exportDir.mkdirs(); }

        File fileAcc = new File(exportDir, "acc.csv");
        File fileGPS = new File(exportDir, "gps.csv");
        try {
            Log.d("latitude", "check");
            fileAcc.createNewFile();

            fileGPS.createNewFile();

            CSVWriter csvWriteAcc = new CSVWriter(new FileWriter(fileAcc));
            CSVWriter csvWriteGps = new CSVWriter(new FileWriter(fileGPS));

            Cursor c = MainActivity.database.query(
                    SensorDbSchema.AccelerometerTable.Name,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

             SensorCursorWrapper cursor = new SensorCursorWrapper(c);

            csvWriteAcc.writeNext(cursor.getColumnNames());

            while(cursor.moveToNext()) {

                String arrStr[]=null;
                String[] mySecondStringArray = new String[cursor.getColumnNames().length];
                for(int i=0;i<cursor.getColumnNames().length;i++)
                {
                    Log.d("count", cursor.getString(i));
                    mySecondStringArray[i] =cursor.getString(i);
                }
                csvWriteAcc.writeNext(mySecondStringArray);
            }
            cursor.close();

            Cursor cgps = MainActivity.database.query(
                    SensorDbSchema.GPSTable.Name,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            final SensorCursorWrapper cursorgps = new SensorCursorWrapper(cgps);
            csvWriteGps.writeNext(cursorgps.getColumnNames());
            while(cursorgps.moveToNext()) {
                String arrStr[]=null;
                String[] mySecondStringArray = new String[cursorgps.getColumnNames().length];
                for(int i=0;i<cursorgps.getColumnNames().length;i++)
                {
                    mySecondStringArray[i] =cursorgps.getString(i);
                }
                csvWriteGps.writeNext(mySecondStringArray);
            }
            cursorgps.close();

            Log.d("latitude", "done");
            return true;
        } catch (IOException e) {
            Log.d("latitude", "done2");
            return false;
        }
    }


}
