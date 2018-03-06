package edu.cmu.eps.scams.recordings;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import edu.cmu.eps.scams.files.DirectoryOutputFileFactory;
import edu.cmu.eps.scams.utilities.RunnableService;

/*
* This class provides an Android "Service" that instructs a background thread to start and stop
* recording audio from the microphone.
* */
public class RecordingService extends Service {

    private static final String TAG = "RecordingService";
    private static final String RECORDINGS_DIRECTORY = "recordings";

    private final RunnableService runnableService;
    private final RecordEventReceiver recordEventReceiver;

    public RecordingService() {
        this.runnableService = new RunnableService(
                new RecordingStoppableFactory(
                        new DirectoryOutputFileFactory(
                                //this.getApplicationContext().getFilesDir(),
                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                                RECORDINGS_DIRECTORY
                        )
                )
        );
        this.recordEventReceiver = new RecordEventReceiver();
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Recording service created");
        TelephonyManager telephonyManager = this.getApplicationContext().getSystemService(TelephonyManager.class);
        telephonyManager.listen(new RecordingPhoneStateListener(this.getApplicationContext()), PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "RecordingService started");
        int result = START_NOT_STICKY;
        if (intent == null) {
            Log.d(TAG, "RecordingService no event");
            result = START_NOT_STICKY;
        } else {
            RecordingEvents operation = RecordingEvents.valueOf(intent.getStringExtra("operation"));
            switch (operation) {
                case START:
                    Log.d(TAG, "RecordingService start recording");
                    this.runnableService.start();
                    result = START_STICKY;
                    break;
                case STOP:
                    Log.d(TAG, "RecordingService stop recording");
                    this.runnableService.stop();
                    result = START_STICKY;
                    break;
                default:
                    Log.d(TAG, "RecordingService no event");
                    break;
            }
        }
        return result;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
