package edu.cmu.eps.scams.recordings;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import edu.cmu.eps.scams.files.IOutputFileFactory;
import edu.cmu.eps.scams.recognition.VoiceRecognitionFacade;

/**
 * Created by thoma on 3/10/2018.
 */

public class RecordingServiceHandler extends Handler {

    private static final String TAG = "RecordingServiceHandler";
    private final IOutputFileFactory fileFactory;
    private final RecorderFacade recorder;
    private final VoiceRecognitionFacade recognizer;

    public RecordingServiceHandler(Looper looper, IOutputFileFactory fileFactory, Context context) {
        super(looper);
        this.fileFactory = fileFactory;
        this.recorder = new RecorderFacade();
        this.recognizer = new VoiceRecognitionFacade(context);
    }

    @Override
    public void handleMessage(Message message) {
        Log.d(TAG, String.format("Message received: %d %d", message.arg1, message.arg2));
        RecordingEvents event = RecordingEvents.fromInt(message.arg2);
        try {
            switch (event) {
                case NONE:
                    Log.d(TAG, "No recording event");
                    break;
                case START:
                    Log.d(TAG, "Start recording event");
                    this.recorder.start(this.fileFactory.build());
                    this.recognizer.start();
                    break;
                case STOP:
                    Log.d(TAG, "Stop recording event");
                    this.recorder.stop();
                    this.recognizer.stop();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Log.d(TAG, String.format("Encountered exception: %s", e.getMessage()));
        }
    }
}
