package edu.cmu.eps.scams.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import edu.cmu.eps.scams.logic.model.ClassifierParameters;
import edu.cmu.eps.scams.logic.IApplicationLogic;
import edu.cmu.eps.scams.logic.LogicFactory;
import edu.cmu.eps.scams.notifications.NotificationFacade;
import edu.cmu.eps.scams.transcription.TranscriptionRunnable;

public class TranscriptionService extends Service {

    private static final String TAG = "TranscriptionService";
    private static final String HANDLER_THREAD_NAME = "TranscriptionServiceThread";
    private HandlerThread handlerThread;
    private Looper handlerLooper;
    private Handler handler;
    private IApplicationLogic logic;
    private ClassifierParameters classifierParameters;

    public TranscriptionService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Transcription service created");
        this.handlerThread = new HandlerThread(HANDLER_THREAD_NAME);
        this.handlerThread.start();
        this.handlerLooper = this.handlerThread.getLooper();
        this.handler = new Handler(this.handlerLooper);
        this.logic = LogicFactory.build(this);
        this.classifierParameters = this.logic.getClassifierParameters();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Transcription service started");
        int result = START_STICKY;
        if (intent == null || intent.getLongExtra("ring_timestamp", -1) < 0) {
            Log.d(TAG, "Transcription service no event");
            result = START_STICKY;
        } else {
            String audioRecordingPath = intent.getStringExtra("audio_recording");
            String incomingNumber = intent.getStringExtra("incoming_number");
            long ringTimestamp = intent.getLongExtra("ring_timestamp", -1);
            long audioLength = intent.getLongExtra("audio_length", -1);
            this.handler.post(new TranscriptionRunnable(
                    audioRecordingPath,
                    incomingNumber,
                    ringTimestamp,
                    audioLength,
                    this.classifierParameters,
                    this.logic,
                    new NotificationFacade(this),
                    this));
        }
        return result;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
