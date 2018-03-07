package edu.cmu.eps.scams.recognition;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import edu.cmu.eps.scams.recordings.RecordingEvents;
import edu.cmu.eps.scams.recordings.RecordingPhoneStateListener;

public class VoiceRecognitionService extends Service {

    private static final String TAG = "VoiceRecognitionService";
    private VoiceRecognitionRunnable voiceRecognizer;

    public VoiceRecognitionService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Voice recognition service created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "VoiceRecognitionService started");
        int result = START_NOT_STICKY;
        if (intent == null) {
            Log.d(TAG, "VoiceRecognitionService no event");
            result = START_NOT_STICKY;
        } else {
            RecordingEvents operation = RecordingEvents.valueOf(intent.getStringExtra("operation"));
            switch (operation) {
                case START:
                    Log.d(TAG, "VoiceRecognitionService start recording");
                    this.start();
                    result = START_STICKY;
                    break;
                case STOP:
                    Log.d(TAG, "VoiceRecognitionService stop recording");
                    this.stop();
                    result = START_STICKY;
                    break;
                default:
                    Log.d(TAG, "VoiceRecognitionService no event");
                    break;
            }
        }
        return result;
    }

    private void start() {
        try {
            this.voiceRecognizer = new VoiceRecognitionRunnable(this.getApplicationContext());
            Handler loopHandler = new Handler(Looper.getMainLooper());
            loopHandler.post(voiceRecognizer);
        } catch (Exception e) {
            Log.d(TAG, String.format("Error on start: %s", e.getMessage()));
        }
    }

    private void stop() {
        try {
            if (this.voiceRecognizer != null) {
                this.voiceRecognizer.stop();
            }
        } catch (Exception e) {
            Log.d(TAG, String.format("Error on stop: %s", e.getMessage()));
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
