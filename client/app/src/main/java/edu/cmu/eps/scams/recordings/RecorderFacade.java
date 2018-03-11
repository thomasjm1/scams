package edu.cmu.eps.scams.recordings;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by thoma on 3/11/2018.
 */

public class RecorderFacade {

    private static final String TAG = "RecorderFacade";
    private static final int MAX_RECORDING_INTERVALS = 60;
    private static final int RECORDING_RATE = 8000;
    private static final int CHANNEL_IN = AudioFormat.CHANNEL_IN_MONO;
    private static final int CHANNELS_OUT = AudioFormat.CHANNEL_OUT_MONO;
    private static final int FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private static int BUFFER_SIZE = AudioRecord
            .getMinBufferSize(RECORDING_RATE, CHANNEL_IN, FORMAT);

    private AtomicBoolean recordingFlag;
    private AudioRecord audioRecord;
    private final MediaRecorder mediaRecorder;

    public RecorderFacade() {
        this.recordingFlag = new AtomicBoolean(false);
        this.mediaRecorder = new MediaRecorder();
    }

    public boolean isRecording() {
        return this.recordingFlag.get();
    }

    public void start(File target) throws IOException {
        if (this.recordingFlag.getAndSet(true) == false) {
            Log.d(TAG, String.format("Start recording to: %s", target.getAbsolutePath()));
            this.mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            this.mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            this.mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            this.mediaRecorder.setOutputFile(target.getAbsolutePath());
            this.mediaRecorder.prepare();
            this.mediaRecorder.start();
        } else {
            Log.d(TAG, String.format("Recording already started: %s", target.getAbsolutePath()));
        }
    }

    public void stop() {
        if (this.recordingFlag.getAndSet(false) == true) {
            try {
                Log.d(TAG, "Stopping audio record");
                this.mediaRecorder.stop();
                this.mediaRecorder.reset();
            } catch (Exception exception) {
                Log.d(TAG, String.format("Failed to stop recording due to %s", exception.getMessage()));
                throw new RuntimeException("Failed to stop recording", exception);
            }
        } else {
            Log.d(TAG, "Recording not in progress");
        }
    }
}
