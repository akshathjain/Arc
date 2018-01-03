package com.akshathjain.arc.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Akshath on 1/2/2018.
 */

public class BatteryService extends Service {
    private final IBinder reference = new BatteryServiceBinder();
    private BatteryManager bm;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return reference;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bm = (BatteryManager) getApplicationContext().getSystemService(Context.BATTERY_SERVICE);

        return Service.START_STICKY;
    }

    public double getCurrentNow(){
        return (double) bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW) / 1000;
    }

    public boolean isCharging(){
        System.out.println(bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_STATUS));
        return true;
    }

    public class BatteryServiceBinder extends Binder {
        public BatteryService getService() {
            return BatteryService.this;
        }
    }
}
