package edu.cmu.eps.scams.recordings;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import edu.cmu.eps.scams.notifications.NotificationFacade;
import edu.cmu.eps.scams.recognition.VoiceRecognitionService;

/**
 * Created by thoma on 3/5/2018.
 * Listens for changes to phone state and broadcasts
 */

public class RecordingPhoneStateListener extends PhoneStateListener {

    private static String TAG = "RecordingPhoneStateListener";

    private final Context context;

    private final NotificationFacade notificationFacade;

    public RecordingPhoneStateListener(Context context) {
        this.context = context;
        this.notificationFacade = new NotificationFacade(context);
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d(TAG, "PHONE IS IDLE");
                context.startService(new Intent(context, RecordingService.class)
                        .putExtra("operation", RecordingEvents.STOP.ordinal()));
                this.notificationFacade.create(this.context, "Phone Status", "Phone is idle");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d(TAG, "PHONE IS OFF THE HOOK");
                context.startService(new Intent(context, RecordingService.class)
                        .putExtra("operation", RecordingEvents.START.ordinal()));
                this.notificationFacade.create(this.context, "Phone Status", "Phone is off hook");
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d(TAG, "PHONE IS RINGING");
                context.startService(new Intent(context, RecordingService.class)
                        .putExtra("operation", RecordingEvents.START.ordinal()));
                this.notificationFacade.create(this.context, "Phone Status", "Phone is ringing");
                break;
            default:
                Log.d(TAG, String.format("Unknown phone event: %d", state));
                break;
        }
    }
}
