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
import edu.cmu.eps.scams.recordings.AudioRecordFacade;
import edu.cmu.eps.scams.recordings.IRecorder;
import edu.cmu.eps.scams.recordings.PhoneCallResult;

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
        Log.d(TAG, String.format("OutgoingMessage received: %d %d", message.arg1, message.arg2));
        RecordingEvents event = RecordingEvents.fromInt(message.arg2);
        try {
            switch (event) {
                case NONE:
                    Log.d(TAG, "No recording event");
                    break;
                case RESET:
                    Log.d(TAG, "No recording event, verifying functionality");
                    break;
                case START:
                    Log.d(TAG, "Start recording event");
                    if (this.recorder.isRecording() == false) {
                        this.incomingNumber = message.getData().getString("incomingNumber");
                        this.recorder.start(this.fileFactory.build());
                        this.sendMessageDelayed(this.buildLoopEventMessage(), this.loopEventDelay);
                    }
                    break;
                case STOP:
                    Log.d(TAG, "Stop recording event");
                    this.recorder.stop();
                    break;
                case LOOP:
                    Log.d(TAG, "Loop recording event");
                    if (this.recorder.isRecording()) {
                        Collection<PhoneCallResult> results = this.recorder.loopEvent();
                        if (results.size() > 0) {
                            Log.d(TAG, "Loop event returned a PhoneCallResult to process");
                            PhoneCallResult input = results.iterator().next();
                            Log.d(TAG, "Loop event sending start to transcription service");
                            this.context.startService(new Intent(context, TranscriptionService.class)
                                    .putExtra("audio_recording", input.audioRecording.getAbsolutePath())
                                    .putExtra("incoming_number", this.incomingNumber)
                                    .putExtra("ring_timestamp", input.ringTimestamp)
                                    .putExtra("audio_length", input.audioLength)
                            );
                        }
                        if (this.telephonyManager.getCallState() == TelephonyManager.CALL_STATE_IDLE) {
                            Log.d(TAG, "Stop recording due to idle state");
                            this.recorder.stop();
                        }
                        this.sendMessageDelayed(this.buildLoopEventMessage(), this.loopEventDelay);
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Log.d(TAG, String.format("Encountered exception: %s", e.getMessage()));
        }
    }

    private Message buildLoopEventMessage() {
        return this.obtainMessage(1, RecordingEvents.LOOP.ordinal(), RecordingEvents.LOOP.ordinal());
    }
}
