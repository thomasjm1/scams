package edu.cmu.eps.scams.recordings;

import android.app.Service;
import android.content.Intent;
import android.icu.text.AlphabeticIndex;
import android.os.Environment;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import edu.cmu.eps.scams.files.DirectoryOutputFileFactory;

/*
* This class provides an Android "Service" that instructs a background thread to start and stop
* recording audio from the microphone.
* */
public class RecordingService extends Service {

    private static final String TAG = "RecordingService";
    private static final String HANDLER_THREAD_NAME = "RECORDING_HANDLER_THREAD";
    private static final String RECORDING_DIRECTORY = "recordings";

    private HandlerThread handlerThread;
    private Looper handlerLooper;
    private RecordingServiceHandler serviceHandler;

    public RecordingService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Create recording service handler thread");
        this.handlerThread = new HandlerThread(HANDLER_THREAD_NAME);
        this.handlerThread.start();
        this.handlerLooper = this.handlerThread.getLooper();
        this.serviceHandler = new RecordingServiceHandler(
                this.handlerLooper,
                new DirectoryOutputFileFactory(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), RECORDING_DIRECTORY),
                this.getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Recording service onStartCommand received");
        Message message = this.serviceHandler.obtainMessage();
        message.arg1 = startId;
        message.arg2 = intent.getIntExtra("operation", RecordingEvents.NONE.ordinal());
        this.serviceHandler.sendMessage(message);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
