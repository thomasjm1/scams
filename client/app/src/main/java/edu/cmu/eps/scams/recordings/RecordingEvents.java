package edu.cmu.eps.scams.recordings;

/**
 * Created by thoma on 3/5/2018.
 * Represents the events we need to support for recording
 */

public enum RecordingEvents {
    START,
    STOP,
    NONE;

    public static RecordingEvents fromInt(int value) {
        return RecordingEvents.values()[value];
    }
}
