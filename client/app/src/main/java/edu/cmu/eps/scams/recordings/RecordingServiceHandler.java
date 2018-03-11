package edu.cmu.eps.scams.recordings;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import edu.cmu.eps.scams.files.IOutputFileFactory;

/**
 * Created by thoma on 3/10/2018.
 */

public class RecordingServiceHandler extends Handler {

    private static final String TAG = "RecordingServiceHandler";
    private final IOutputFileFactory fileFactory;
    private final RecorderFacade recorder;

    public RecordingServiceHandler(Looper looper, IOutputFileFactory fileFactory) {
        super(looper);
        this.fileFactory = fileFactory;
        this.recorder = new RecorderFacade();
    }

    @Override
    public void handleMessage(Message message) {
        Log.d(TAG, String.format("Message received: %d %d", message.arg1, message.arg2));
        RecordingEvents event = RecordingEvents.fromInt(message.arg2);
        switch (event) {
            case NONE:
                Log.d(TAG, "No recording event");
                break;
            case START:
                Log.d(TAG, "Start recording event");
                this.recorder.start(this.fileFactory.build());
                break;
            case STOP:
                Log.d(TAG, "Stop recording event");
                this.recorder.stop();
                break;
            default:
                break;
        }
    }
}
