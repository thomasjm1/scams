package edu.cmu.eps.scams.recordings;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by thoma on 3/5/2018.
 * Listens for changes to phone state and send to recording service
 */

public class RecordingPhoneStateListener extends PhoneStateListener {

    private static String TAG = "RecordingPhoneStateListener";

    private final Context context;

    public RecordingPhoneStateListener(Context context) {
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d(TAG, "PHONE IS IDLE");
                context.startService(new Intent(context, RecordingService.class)
                        .putExtra("operation", RecordingEvents.STOP.name()));
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d(TAG, "PHONE IS OFF THE HOOK");
                context.startService(new Intent(context, RecordingService.class)
                        .putExtra("operation", RecordingEvents.START.name()));
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d(TAG, "PHONE IS RINGING");
                context.startService(new Intent(context, RecordingService.class)
                        .putExtra("operation", RecordingEvents.START.name()));
                break;
        }
    }
}
