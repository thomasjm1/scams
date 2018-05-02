package edu.cmu.eps.scams.services;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Collection;

import edu.cmu.eps.scams.files.IOutputFileFactory;
import edu.cmu.eps.scams.notifications.NotificationFacade;
import edu.cmu.eps.scams.recordings.AudioRecordFacade;
import edu.cmu.eps.scams.recordings.IRecorder;
import edu.cmu.eps.scams.recordings.PhoneCallResult;
import edu.cmu.eps.scams.transcription.TranscriptionLogic;

/**
 * Created by jeremy on 3/10/2018.
 * The background Handler that runs on another thread and does the work of recording audio
 */
public class RecordingServiceHandler extends Handler {

    private static final String TAG = "RecordingServiceHandler";
    private final IOutputFileFactory fileFactory;
    private final IRecorder recorder;
    private final int loopEventDelay;
    private final Context context;
    private final TelephonyManager telephonyManager;
    private String incomingNumber;

    public RecordingServiceHandler(Looper looper, IOutputFileFactory fileFactory, Context context) {
        super(looper);
        this.context = context;
        this.fileFactory = fileFactory;
        this.loopEventDelay = 1000;
        this.recorder = new AudioRecordFacade();
        this.telephonyManager = this.context.getSystemService(TelephonyManager.class);
    }

    @Override
    public void handleMessage(Message message) {
        Log.i(TAG, String.format("Recording event received: %d %d", message.arg1, message.arg2));
        RecordingEvents event = RecordingEvents.fromInt(message.arg2);
        try {
            switch (event) {
                case NONE:
                    Log.i(TAG, "No recording event");
                    break;
                case RESET:
                    Log.i(TAG, "No recording event, verifying functionality");
                    break;
                case START:
                    if (this.recorder.isRecording() == false) {
                        Log.i(TAG, "Start recording event");
                        this.incomingNumber = message.getData().getString("incomingNumber");
                        this.recorder.start(this.fileFactory.build());
                        this.sendMessageDelayed(this.buildLoopEventMessage(), this.loopEventDelay);
                    } else {
                        Log.i(TAG, "Continue recording event");
                    }
                    break;
                case STOP:
                    Log.i(TAG, "Stop recording event");
                    PhoneCallResult stoppedResult = this.recorder.stop();
                    Log.i(TAG, "Loop event sending start to transcription service");
                    if (stoppedResult != null) {
                        TranscriptionLogic.handle(
                                this.context,
                                stoppedResult.audioRecording.getAbsoluteFile(),
                                new NotificationFacade(this.context),
                                stoppedResult.ringTimestamp,
                                this.incomingNumber
                        );
                    }
                    break;
                case LOOP:
                    Log.i(TAG, "Loop recording event");
                    if (this.recorder.isRecording()) {
                        Collection<PhoneCallResult> results = this.recorder.loopEvent();
                        if (results.size() > 0) {
                            Log.i(TAG, "Loop event returned a PhoneCallResult to process");
                            PhoneCallResult input = results.iterator().next();
                            Log.i(TAG, "Loop event sending start to transcription service");
                            TranscriptionLogic.handle(
                                    this.context,
                                    input.audioRecording.getAbsoluteFile(),
                                    new NotificationFacade(this.context),
                                    input.ringTimestamp,
                                    this.incomingNumber
                            );
                        }
                        if (this.telephonyManager.getCallState() == TelephonyManager.CALL_STATE_IDLE) {
                            Log.i(TAG, "Stop recording due to idle state");
                            this.recorder.stop();
                        }
                        this.sendMessageDelayed(this.buildLoopEventMessage(), this.loopEventDelay);
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Log.i(TAG, String.format("Encountered exception: %s", e.getMessage()));
        }
    }

    private Message buildLoopEventMessage() {
        return this.obtainMessage(1, RecordingEvents.LOOP.ordinal(), RecordingEvents.LOOP.ordinal());
    }
}
