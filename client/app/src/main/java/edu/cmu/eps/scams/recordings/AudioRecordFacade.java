package edu.cmu.eps.scams.recordings;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.SystemClock;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import edu.cmu.eps.scams.utilities.WavUtility;

/**
 * Created by thoma on 3/11/2018.
 */

public class AudioRecordFacade extends BaseRecorder {

    private static final String TAG = "AudioRecordFacade";


    private AudioRecord recorder;
    private FileOutputStream fileOutputStream;
    private final byte[] buffer;
    private int bytesRead;
    private File file;

    public AudioRecordFacade() {
        this.buffer = new byte[READ_BUFFER_SIZE];
    }

    @Override
    protected void startRecording(File target) throws RecordingException {
        Log.d(TAG, "Starting recording with AudioRecord");
        this.bytesRead = 0;
        this.file = target;
        try {
            this.recorder = new AudioRecord(AUDIO_SOURCE, SAMPLE_RATE, CHANNEL_MASK, ENCODING, BUFFER_SIZE);
            Log.d(TAG, "Built AudioRecord");
            this.fileOutputStream = new FileOutputStream(target);
            Log.d(TAG, "Opened output file stream");
            // Write out the wav file header
            WavUtility.writeWavHeader(fileOutputStream, CHANNEL_MASK, SAMPLE_RATE, ENCODING);
            Log.d(TAG, "Wrote WAV file header to output file stream");
            Log.d(TAG, "Starting to record");
            this.recorder.startRecording();
            Log.d(TAG, "Recording in progress");
        } catch (IOException exception) {
            Log.d(TAG, String.format("Exception during start of recording: %s", exception.getMessage()));
        }
    }

    @Override
    public void loopEvent() {
        Log.d(TAG, "Recording loop event");
        if (this.isRecording()) {
            this.readFromAudioRecord();
        }
    }

    private int readFromAudioRecord() {
        int result = 0;
        try {
            int read = this.recorder.read(buffer, 0, buffer.length);
            // WAVs cannot be > 4 GB due to the use of 32 bit unsigned integers.
            if (this.bytesRead + read > 4294967295L) {
                Log.d(TAG, "File max size exceeded");
            } else {
                fileOutputStream.write(buffer, 0, read);
                this.bytesRead += read;
            }
            result = read;
        } catch (IOException exception) {
            Log.d(TAG, String.format("Encountered IO exception while writing: %s", exception.getMessage()));
        }
        return result;
    }

    @Override
    public void stopRecording() throws RecordingException {
        try {
            Log.d(TAG, "Stopping recording");
            if (this.recorder.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                this.recorder.stop();
            }
        } catch (IllegalStateException exception) {
            Log.d(TAG, String.format("Exception during stopping of recording: %s", exception.getMessage()));
        }
        int lastRead = 1;
        while (lastRead > 0) {
            lastRead = this.readFromAudioRecord();
        }
        if (this.recorder.getState() == AudioRecord.STATE_INITIALIZED) {
            Log.d(TAG, "Releasing AudioRecord");
            this.recorder.release();
        }
        if (fileOutputStream != null) {
            try {
                Log.d(TAG, "Closing output file");
                fileOutputStream.close();
            } catch (IOException exception) {
                Log.d(TAG, String.format("IO exception: %s", exception.getMessage()));
            }
        }
        try {
            Log.d(TAG, "Update the WAV file header");
            WavUtility.updateWavHeader(this.file);
        } catch (IOException exception) {
            Log.d(TAG, "Exception encountered on closing of WAV file");
        }
    }
}
