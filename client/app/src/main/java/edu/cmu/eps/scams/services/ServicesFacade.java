package edu.cmu.eps.scams.services;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

/**
 * Created by jeremy on 3/10/2018.
 * Starts background services since there are too many, run from main activity
 */

public class ServicesFacade {

    private static final String TAG = "ServicesFacade";

    public static void startServices(Activity activity) {
        Log.d(TAG, "Starting services");
        Intent recordingServiceIntent = new Intent(activity, PhoneEventService.class)
                .putExtra("operation", RecordingEvents.NONE.name());
        activity.startService(recordingServiceIntent);

        Intent messagingServiceIntent = new Intent(activity, MessagingService.class);
        activity.startService(messagingServiceIntent);
    }
}
