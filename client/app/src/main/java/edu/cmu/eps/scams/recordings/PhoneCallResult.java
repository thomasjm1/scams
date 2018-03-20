package edu.cmu.eps.scams.recordings;

import java.io.File;

/**
 * Created by thoma on 3/19/2018.
 */

public class PhoneCallResult {
    public final long ringTimestamp;
    public final int audioLength;
    public final File audioRecording;

    public PhoneCallResult(long ringTimestamp, File audioRecording, int audioLength) {
        this.ringTimestamp = ringTimestamp;
        this.audioRecording = audioRecording;
        this.audioLength = audioLength;
    }
}
