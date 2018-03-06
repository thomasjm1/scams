package edu.cmu.eps.scams.recordings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

/*
* This class provides functionality for identify state changes of interest for phone calls. Such as
* when the phone is ringing.
* */
public class RecordEventReceiver extends BroadcastReceiver {

    private static final String TAG = "RecordEventReceiver";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.d(TAG, "Record Event Receiver message received!");
        String stateName = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
        String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        int state = -1;

        if (stateName.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            Log.d(TAG, "PHONE IS IDLE");
            context.startService(new Intent(context, RecordingService.class)
                    .putExtra("operation", 2));
        } else if (stateName.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            Log.d(TAG, "PHONE IS OFF THE HOOK");
            context.startService(new Intent(context, RecordingService.class)
                    .putExtra("operation", 1));
        } else if (stateName.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            Log.d(TAG, "PHONE IS RINGING");
            context.startService(new Intent(context, RecordingService.class)
                    .putExtra("operation", 1));
        }
    }
}
