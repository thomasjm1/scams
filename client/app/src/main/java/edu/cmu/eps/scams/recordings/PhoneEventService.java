package edu.cmu.eps.scams.recordings;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;


/*
* This class provides an Android "Service" that listens for phone state changes.
* */
public class PhoneEventService extends Service {

    private static final String TAG = "PhoneEventService";

    public PhoneEventService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Phone event service created, listening for phone events");
        TelephonyManager telephonyManager = this.getApplicationContext().getSystemService(TelephonyManager.class);
        telephonyManager.listen(new RecordingPhoneStateListener(this.getApplicationContext()), PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Phone event service started, listening for phone events with restart");
        int result = START_NOT_STICKY;
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
