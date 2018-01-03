package com.akshathjain.arc.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
    private boolean isCharging;

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

    private Intent getBatteryStatus(){
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        return getApplicationContext().registerReceiver(null, iFilter);
    }

    public double getCurrentNow(){
        return -1 * (double) bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW) / 1000;
    }

    public double getRemainingEnergy(){
        return (double) bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER) / Math.pow(10, 9);
    }

    public boolean getBatteryCold(){
        return getBatteryStatus().getIntExtra(BatteryManager.EXTRA_HEALTH, -1) == BatteryManager.BATTERY_HEALTH_COLD;
    }

    public boolean getBatteryDead(){
        return getBatteryStatus().getIntExtra(BatteryManager.EXTRA_HEALTH, -1) == BatteryManager.BATTERY_HEALTH_DEAD;
    }

    public boolean getBatteryGood(){
        return getBatteryStatus().getIntExtra(BatteryManager.EXTRA_HEALTH, -1) == BatteryManager.BATTERY_HEALTH_GOOD;
    }

    public boolean getBatteryOverheat(){
        return getBatteryStatus().getIntExtra(BatteryManager.EXTRA_HEALTH, -1) == BatteryManager.BATTERY_HEALTH_OVERHEAT;
    }

    public boolean getBatteryOvervolt(){
        return getBatteryStatus().getIntExtra(BatteryManager.EXTRA_HEALTH, -1) == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE;
    }

    public int getPercentage(){
        return (int) (100 * (double) getBatteryStatus().getIntExtra(BatteryManager.EXTRA_LEVEL, 0) / getBatteryStatus().getIntExtra(BatteryManager.EXTRA_SCALE, -1));
    }

    public double getTemperature(){
        return 9.0 / 5 * ((double) getBatteryStatus().getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10) + 32;
    }

    public String getTechnology(){
        return getBatteryStatus().getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
    }

    public double getVoltage(){
        return getBatteryStatus().getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) / 1000.0;
    }

    public boolean isCharging(){
        int status = getBatteryStatus().getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        return status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
    }

    public String isPluggedAC(){
        return bm.getIntProperty(BatteryManager.BATTERY_PLUGGED_AC) + "";
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

    @Override
    public String toString() {
        return "Current Now: " + getCurrentNow() + "\n" +
                "Remaining Energy: " + getRemainingEnergy() + "\n" +
                "BatteryCold: " + getBatteryCold() + "\n" +
                "BatteryDead: " + getBatteryDead() + "\n" +
                "BatteryGood: " + getBatteryGood() + "\n" +
                "BatteryOverheat: " + getBatteryOverheat() + "\n" +
                "BatteryOverVolt: " + getBatteryOvervolt() + "\n" +
                "Percentage: " + getPercentage() + "\n" +
                "IsCharging: " + isCharging() + "\n" +
                "PluggedAC: " + isPluggedAC() + "\n" +
                "PluggedUSB: " + isPluggedUSB() + "\n" +
                "PluggedWireless: " + isPluggedWireless() + "\n" +
                "Temperature: " + getTemperature() + "F\n" +
                "Technology: " + getTechnology() + "\n" +
                "Voltage: " + getVoltage();
    }
}
