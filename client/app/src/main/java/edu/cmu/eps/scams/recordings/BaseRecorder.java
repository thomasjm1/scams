package edu.cmu.eps.scams.recordings;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by thoma on 3/17/2018.
 */

public abstract class BaseRecorder implements IRecorder {

    private static final String TAG = "BaseRecorder";

    private AtomicBoolean recordingFlag;

    public BaseRecorder() {
        this.recordingFlag = new AtomicBoolean(false);
    }

    @Override
    public boolean isRecording() {
        return this.recordingFlag.get();
    }

    @Override
    public void start(File target)  {
        if (this.recordingFlag.getAndSet(true) == false) {
            Log.d(TAG, String.format("Start recording to: %s", target.getAbsolutePath()));
            try {
                this.startRecording(target);
            } catch (RecordingException e) {
                Log.d(TAG, String.format("Caught exception: %s", e.getMessage()));
            }
        } else {
            Log.d(TAG, String.format("Recording already started: %s", target.getAbsolutePath()));
        }
    }

    public void stop() {
        if (this.recordingFlag.getAndSet(false) == true) {
            try {
                Log.d(TAG, "Stopping audio record");
                this.stopRecording();
            } catch (RecordingException exception) {
                Log.d(TAG, String.format("Failed to stop recording due to %s", exception.getMessage()));
                throw new RuntimeException("Failed to stop recording", exception);
            }
        } else {
            Log.d(TAG, "Recording not in progress");
        }
    }

    protected abstract void startRecording(File target) throws RecordingException;

    protected abstract void stopRecording() throws RecordingException;

}
