package com.example.a2017067_assignment3;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class WifiService extends Service{
    private WifiManager mWifiManager;
    String[] names, strengths, timestamps;

    @Override
    public void onCreate() {
        super.onCreate();

        registerReceiver(new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                unregisterReceiver(this);
                final WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(context.WIFI_SERVICE);
                int state = wifi.getWifiState();
                if(state == WifiManager.WIFI_STATE_ENABLED) {
                    List<ScanResult> results = wifi.getScanResults();
//                    ArrayList<String> apnames = new ArrayList<>();
//                    ArrayList<String> apstrengths = new ArrayList<>();
//                    ArrayList<String> times = new ArrayList<>();

                    StringBuilder apnames = new StringBuilder();
                    StringBuilder apstrengths = new StringBuilder();

                    for (ScanResult result : results) {
                        int level = result.level;
                        int difference = level * 100 / result.level;
                        int signalStrangth= 0;
                        if(difference >= 100)
                            signalStrangth = 4;
                        else if(difference >= 75)
                            signalStrangth = 3;
                        else if(difference >= 50)
                            signalStrangth = 2;
                        else if(difference >= 25)
                            signalStrangth = 1;
                        apstrengths.append(Integer.toString(signalStrangth) + ", ");
                        apnames.append(result.SSID + ", ");

                    }
                    Long tsLong = System.currentTimeMillis()/1000;
                    String ts = tsLong.toString();
                    Intent ii = new Intent("BORING2");
                    String names = apnames.toString();
                    String strengths = apstrengths.toString();
                    ii.putExtra("timestamps", ts);
                    ii.putExtra("names", names);
                    ii.putExtra("str", strengths);
                    sendBroadcast(ii);
                }
            }
        }, new IntentFilter(WifiManager.RSSI_CHANGED_ACTION));



    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}