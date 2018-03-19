package edu.cmu.eps.scams.services;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import edu.cmu.eps.scams.files.IOutputFileFactory;
import edu.cmu.eps.scams.recordings.AudioRecordFacade;
import edu.cmu.eps.scams.recordings.IRecorder;

/**
 * Created by thoma on 3/10/2018.
 */

public class RecordingServiceHandler extends Handler {

    private static final String TAG = "RecordingServiceHandler";
    private final IOutputFileFactory fileFactory;
    private final IRecorder recorder;
    private final int loopEventDelay;
    private boolean stopFlag;

    public RecordingServiceHandler(Looper looper, IOutputFileFactory fileFactory, Context context) {
        super(looper);
        this.fileFactory = fileFactory;
        this.loopEventDelay = 1000;
        this.recorder = new AudioRecordFacade();
    }

    @Override
    public void handleMessage(Message message) {
        Log.d(TAG, String.format("Message received: %d %d", message.arg1, message.arg2));
        RecordingEvents event = RecordingEvents.fromInt(message.arg2);
        try {
            switch (event) {
                case NONE:
                    Log.d(TAG, "No recording event");
                    break;
                case RESET:
                    Log.d(TAG, "No recording event, verifying functionality");
                    break;
                case START:
                    Log.d(TAG, "Start recording event");
                    this.recorder.start(this.fileFactory.build());
                    this.sendMessageDelayed(this.buildLoopEventMessage(), this.loopEventDelay);
                    this.stopFlag = false;
                    break;
                case STOP:
                    Log.d(TAG, "Stop recording event");
                    this.recorder.stop();
                    this.stopFlag = true;
                    break;
                case LOOP:
                    Log.d(TAG, "Loop recording event");
                    if (this.stopFlag == false) {
                        this.recorder.loopEvent();
                        this.sendMessageDelayed(this.buildLoopEventMessage(), this.loopEventDelay);
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Log.d(TAG, String.format("Encountered exception: %s", e.getMessage()));
        }
    }

    private Message buildLoopEventMessage() {
        return this.obtainMessage(1, RecordingEvents.LOOP.ordinal(), RecordingEvents.LOOP.ordinal());
    }
}
