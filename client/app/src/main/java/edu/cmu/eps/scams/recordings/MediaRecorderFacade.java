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

public class MediaRecorderFacade extends BaseRecorder {

    private static final String TAG = "MediaRecorderFacade";
    private static final int MAX_RECORDING_INTERVALS = 60;
    private static final int RECORDING_RATE = 8000;
    private static final int CHANNEL_IN = AudioFormat.CHANNEL_IN_MONO;
    private static final int CHANNELS_OUT = AudioFormat.CHANNEL_OUT_MONO;
    private static final int FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private static int BUFFER_SIZE = AudioRecord
            .getMinBufferSize(RECORDING_RATE, CHANNEL_IN, FORMAT);
    private final MediaRecorder mediaRecorder;

    public MediaRecorderFacade() {
        this.mediaRecorder = new MediaRecorder();
    }

    @Override
    protected void startRecording(File target) throws RecordingException {
        this.mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        this.mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        this.mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        this.mediaRecorder.setOutputFile(target.getAbsolutePath());
        try {
            this.mediaRecorder.prepare();
        } catch (IOException e) {
            throw new RecordingException(e);
        }
        this.mediaRecorder.start();
    }

    @Override
    public void stopRecording() throws RecordingException {
        try {
            Log.d(TAG, "Stopping audio record");
            this.mediaRecorder.stop();
            this.mediaRecorder.reset();
        } catch (Exception exception) {
            Log.d(TAG, String.format("Failed to stop recording due to %s", exception.getMessage()));
            throw new RecordingException("Failed to stop recording", exception);
        }
    }

    @Override
    public void loopEvent() {

    }
}
