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
        return -1 * (double) bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW) / 1000;
    }

    public double getRemainingEnergy(){
        return (double) bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER) / Math.pow(10, 9);
    }

    public boolean getBatteryCold(){
        return bm.getIntProperty(BatteryManager.BATTERY_HEALTH_COLD) == 1;
    }

    public boolean getBatteryDead(){
        return bm.getIntProperty(BatteryManager.BATTERY_HEALTH_DEAD) == 1;
    }

    public boolean getBatteryGood(){
        return bm.getIntProperty(BatteryManager.BATTERY_HEALTH_GOOD) == 1;
    }

    public boolean getBatteryOverheath(){
        return bm.getIntProperty(BatteryManager.BATTERY_HEALTH_OVERHEAT) == 1;
    }

    public boolean getBatteryOvervolt(){
        return bm.getIntProperty(BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) == 1;
    }

    public int getPercentage(){
        return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
    }

    public boolean isCharging(){
        return isPluggedAC() || isPluggedUSB() || isPluggedWireless();
    }

    public boolean isPluggedAC(){
        return bm.getIntProperty(BatteryManager.BATTERY_PLUGGED_AC) == 1;
    }

    public boolean isPluggedUSB(){
        return bm.getIntProperty(BatteryManager.BATTERY_PLUGGED_USB) == 1;
    }

    public boolean isPluggedWireless(){
        return bm.getIntProperty(BatteryManager.BATTERY_PLUGGED_WIRELESS) == 1;
    }

    public class BatteryServiceBinder extends Binder {
        public BatteryService getService() {
            return BatteryService.this;
        }
    }
}
