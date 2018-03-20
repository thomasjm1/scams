package edu.cmu.eps.scams.transcription;

import android.util.Log;
import android.util.Pair;

import java.io.File;

/**
 * Created by thoma on 3/19/2018.
 */

public class TranscriptionRunnable implements Runnable {

    private static final String TAG = "TranscriptionRunnable";
    private final File file;
    private final String incomingNumber;
    private final long ringTimestamp;
    private final long audioLength;

    public TranscriptionRunnable(String audioRecordingPath, String incomingNumber, long ringTimestamp, long audioLength) {
        this.file = new File(audioRecordingPath);
        this.incomingNumber = incomingNumber;
        this.ringTimestamp = ringTimestamp;
        this.audioLength = audioLength;
    }

    @Override
    public void run() {
        try {
            Pair<String, Double> text = TranscriptionUtility.transcribe(this.file);
            Log.d(TAG, String.format("Transcription: %s with %f", text.first, text.second));
        } catch (Exception exception) {
            Log.d(TAG, String.format("Transcription encountered error: %s", exception.getMessage()));
        }
    }
}
