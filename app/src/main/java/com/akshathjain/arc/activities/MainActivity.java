package com.akshathjain.arc.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.akshathjain.arc.R;
import com.akshathjain.arc.services.BatteryService;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Arc");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        //textview
        tv = (TextView) findViewById(R.id.test);

        //start service
        final Intent intent = new Intent(this, BatteryService.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                BatteryService batserv = ((BatteryService.BatteryServiceBinder) iBinder).getService();
                startBatteryMonitor(batserv);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        }, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    private void startBatteryMonitor(final BatteryService service) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                final double x = service.getCurrentNow();
                final boolean ischarge = service.isCharging();
                System.out.println(x + " " + ischarge);

                //now run on ui thread to update
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(x + "\n" + ischarge);
                    }
                });
            }
        }, 0, 1000);
    }
}
