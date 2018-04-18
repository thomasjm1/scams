package edu.cmu.eps.scams.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;


/*
* This class provides an Android "Service" that listens for phone state changes.
* */
public class PhoneEventService extends Service {

    private static final String TAG = "PhoneEventService";

    private boolean setupFlag;

    public PhoneEventService() {
        this.setupFlag = false;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Creating phone event service");
    }

    private void setup() {
        Log.d(TAG, "Phone event service created, listening for phone events");
        Intent intent = new Intent(getApplicationContext(), PhoneEventService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 60000, 10000, pendingIntent);
        TelephonyManager telephonyManager = this.getSystemService(TelephonyManager.class);
        telephonyManager.listen(new RecordingPhoneStateListener(this), PhoneStateListener.LISTEN_CALL_STATE);
        this.setupFlag = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Starting phone event service");
        if (this.setupFlag == false) {
            Log.d(TAG, "Running setup of phone event service");
            this.setup();
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "PhoneEventService is being destroyed by system");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d(TAG, "PhoneEventService task removed");
        Intent intent = new Intent(getApplicationContext(), PhoneEventService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 1000, pendingIntent);
        super.onTaskRemoved(rootIntent);
    }
}
