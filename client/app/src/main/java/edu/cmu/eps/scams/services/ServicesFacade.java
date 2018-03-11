package edu.cmu.eps.scams.services;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import edu.cmu.eps.scams.recordings.PhoneEventService;
import edu.cmu.eps.scams.recordings.RecordingEvents;

/**
 * Created by thoma on 3/10/2018.
 * Starts background services, run from main activity
 */

public class ServicesFacade {

    private static final String TAG = "ServicesFacade";

    public static void startServices(Activity activity) {
        Log.d(TAG, "Starting services");
        Intent recordingServiceIntent = new Intent(activity, PhoneEventService.class)
                .putExtra("operation", RecordingEvents.NONE.name());
        activity.startService(recordingServiceIntent);
        //Send Intent to start background service for recording
        //Intent voiceRecognitionServiceIntent = new Intent(activity, VoiceRecognitionService.class)
        //        .putExtra("operation", RecordingEvents.NONE.name());
        //activity.startService(voiceRecognitionServiceIntent);
    }
}
