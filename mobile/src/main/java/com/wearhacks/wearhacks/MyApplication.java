package com.wearhacks.wearhacks;

import android.app.Application;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;


import android.util.Log;


import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;


/**
 * Created by BrandonTD on 2016-03-20.
 */
public class MyApplication extends Application {

    private BeaconManager beaconManager;
    public static boolean entered = false;
    public static String uuid = "";

    @Override
    public void onCreate() {
        super.onCreate();

        beaconManager = new BeaconManager(getApplicationContext());


        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                Log.d("gabe", "testerino2");
                Log.d("enter", "true");
                uuid = region.getProximityUUID().toString();

                entered = true;
                Log.d("gabe", uuid);
            }
            @Override
            public void onExitedRegion(Region region) {
                entered = false;
                // could add an "exit" notification too if you want (-:
            }
        });

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                Log.d("gabe", "testerino1");
                entered = true;
                beaconManager.startMonitoring(new Region(
                        "monitored region",
                        UUID.fromString("00000000-1111-2222-3333-444444444444"),
                        null, null));
            }
        });
    }


}