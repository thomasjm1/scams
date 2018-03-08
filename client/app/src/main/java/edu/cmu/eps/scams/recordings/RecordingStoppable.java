package edu.cmu.eps.scams.recordings;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.provider.SyncStateContract;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import edu.cmu.eps.scams.files.IOutputFileFactory;
import edu.cmu.eps.scams.utilities.Stoppable;

/**
 * Created by thoma on 3/5/2018.
 * This class encapsulates star and stop functionality for recording from the MediaRecorder.
 */

public class RecordingStoppable extends Stoppable {

    private static final String TAG = "RecordingStoppable";

    private static final int SLEEP_INTERVAL = 1000;

    private static final int MAX_RECORDING_INTERVALS = 60;
    private static final int RECORDING_RATE = 8000; // can go up to 44K, if needed
    private static final int CHANNEL_IN = AudioFormat.CHANNEL_IN_MONO;
    private static final int CHANNELS_OUT = AudioFormat.CHANNEL_OUT_MONO;
    private static final int FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private static int BUFFER_SIZE = AudioRecord
            .getMinBufferSize(RECORDING_RATE, CHANNEL_IN, FORMAT);

    private final File recordingFile;

    private MediaRecorder recorder;
    private int recordingInterval;
    private AudioRecord audioRecord;

    public RecordingStoppable(File recordingFile) {
        this.recordingFile = recordingFile;
    }

    @Override
    protected void setup() {
        Log.d(TAG, String.format("Setup recording stoppable with: %s", this.recordingFile.getAbsolutePath()));

        this.audioRecord = new AudioRecord.Builder()
                .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
                .setAudioFormat(new AudioFormat.Builder()
                        .setEncoding(FORMAT)
                        .setSampleRate(RECORDING_RATE)
                        .setChannelMask(CHANNEL_IN)
                        .build())
                .setBufferSizeInBytes(BUFFER_SIZE)
                .build();
        this.audioRecord.startRecording();
        /*
        this.recorder = new MediaRecorder();
        this.recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
        this.recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        this.recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        this.recorder.setOutputFile(this.recordingFile.getAbsolutePath());
        recorder.setOnErrorListener((MediaRecorder mediaRecorder, int details, int extra) -> {
            Log.d(TAG, String.format("OnErrorListener %d %d", details, extra));
        });
        recorder.setOnInfoListener((MediaRecorder mediaRecorder, int details, int extra) -> {
            Log.d(TAG, String.format("OnErrorListener %d %d", details, extra));
        });
        try {
            this.recorder.prepare();
            this.recorder.start();
        } catch (IOException | RuntimeException exception) {
            Log.d(TAG, String.format("Failed to begin recording due to %s", exception.getMessage()));
            throw new RuntimeException("Cannot start recording", exception);
        }
        */
    }

    @Override
    protected void loop() throws InterruptedException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int read = 1;
        try {
            this.recordingFile.createNewFile();
            FileOutputStream recordingOutputStream = new FileOutputStream(this.recordingFile, false);
            while (read > 0) {
                read = this.audioRecord.read(buffer, 0, buffer.length);
                recordingOutputStream.write(buffer, 0, read);
            }
            recordingOutputStream.close();
        } catch (Exception e) {
            Log.d(TAG, String.format("Failed to open %s due to %s", this.recordingFile.getAbsolutePath(), e.getMessage()));
            throw new RuntimeException(e);
        }
        /*
        this.recordingInterval = this.recordingInterval + 1;
        if (this.recordingInterval > MAX_RECORDING_INTERVALS) {
            throw new RuntimeException("Reached maximum recording length");
        } else {
            Thread.sleep(SLEEP_INTERVAL);
        }
        */
    }

    @Override
    protected void cleanup() {
        try {
            Log.d(TAG, "Stopping audio record");
            this.audioRecord.stop();
            //this.recorder.stop();
            //this.recorder.reset();
            //this.recorder.release();
        } catch (Exception exception) {
            Log.d(TAG, String.format("Failed to stop recording due to %s", exception.getMessage()));
            throw new RuntimeException("Failed to stop recording", exception);
        }
    }
}
