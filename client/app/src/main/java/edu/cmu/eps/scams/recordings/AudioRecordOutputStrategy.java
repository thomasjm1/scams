package edu.cmu.eps.scams.recordings;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import edu.cmu.eps.scams.files.OutputStreamMap;
import edu.cmu.eps.scams.utilities.WavUtility;

import static edu.cmu.eps.scams.recordings.AudioRecording.CHANNEL_MASK;
import static edu.cmu.eps.scams.recordings.AudioRecording.ENCODING;
import static edu.cmu.eps.scams.recordings.AudioRecording.SAMPLE_RATE;

/**
 * Created by thoma on 3/18/2018.
 */

public class AudioRecordOutputStrategy {

    private static final String TAG = "AudioRecordOutputStrategy";
    private final File directory;
    private final OutputStreamMap outputStreams;
    private final int intervalLength;
    private final int intervals;
    private final Map<String, AudioRecording> finished;
    private final Map<String, AudioRecording> inProgress;

    public AudioRecordOutputStrategy(File directory, int intervals, int intervalLength) {
        this.directory = directory;
        this.intervalLength = intervalLength;
        this.outputStreams = new OutputStreamMap();
        this.intervals = intervals;
        this.finished = new TreeMap<>();
        this.inProgress = new TreeMap<>();
    }

    public void init() throws IOException {
        long startTime = System.currentTimeMillis();
        for (int intervalIndex = 0; intervalIndex < this.intervals; intervalIndex++) {
            int intervalStart = intervalIndex * this.intervalLength;
            String identifier = String.format(
                    "recording_0_%d",
                    intervalStart + this.intervalLength);
            File file = this.buildFile(identifier);
            OutputStream outputStream = this.buildOutputStream(file);
            this.outputStreams.add(identifier, outputStream);
            this.inProgress.put(identifier, new AudioRecording(file, startTime, startTime + (intervalStart + this.intervalLength), identifier));
        }
    }

    public Collection<AudioRecording> write(byte[] data) {
        this.outputStreams.write(data);
        long currentTime = System.currentTimeMillis();
        return this.conditionallyFinish(currentTime);
    }

    private Collection<AudioRecording> conditionallyFinish(long currentTime) {
        Collection<AudioRecording> results = new ArrayList<>();
        Collection<String> newlyFinished = new ArrayList<>();
        //Find finished recording and add to collection of finished
        for (Map.Entry<String, AudioRecording> item : this.inProgress.entrySet()) {
            if (item.getValue().shouldStop(currentTime)) {
                this.finished.put(item.getKey(), item.getValue());
                newlyFinished.add(item.getKey());
                OutputStream outputStream = this.outputStreams.remove(item.getKey());
                try {
                    outputStream.close();
                } catch (IOException exception) {
                    Log.d(TAG, String.format("Failed to close: %s", item.getValue().toString()));
                }
            }
        }
        //Remove newly finished from inProgress collection
        for (String identifier : newlyFinished) {
            results.add(this.inProgress.remove(identifier));
        }
        return results;
    }

    private OutputStream buildOutputStream(File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        // Write out the wav file header
        WavUtility.writeWavHeader(fileOutputStream, CHANNEL_MASK, SAMPLE_RATE, ENCODING);
        return fileOutputStream;
    }
    
    private File buildFile(String identifier) {
        return new File(this.directory.getAbsolutePath() + "/" + identifier + ".wav");
    }
}
