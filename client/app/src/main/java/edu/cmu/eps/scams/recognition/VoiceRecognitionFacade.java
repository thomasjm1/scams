package edu.cmu.eps.scams.recognition;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by thoma on 3/11/2018.
 */

public class VoiceRecognitionFacade implements RecognitionListener {

    private final static String TAG = "VoiceRecognitionFacade";

    private SpeechRecognizer speechRecognizer;

    public VoiceRecognitionFacade(Context context) {
        this.speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        this.speechRecognizer.setRecognitionListener(this);
    }

    public void start() {
        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
            speechRecognizer.startListening(intent);
        } catch (Exception exception) {
            Log.d(TAG, String.format("Encountered exception during recognition: %s", exception.getMessage()));
        }
    }

    public void stop() {
        this.speechRecognizer.stopListening();
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.d(TAG, "Ready for speech");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d(TAG, "begin speech");

    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.d(TAG, "rms changed");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.d(TAG, "buffer received");
    }

    @Override
    public void onEndOfSpeech() {
        Log.d(TAG, "end of speech");
    }

    @Override
    public void onError(int error) {
        Log.d(TAG, String.format("error: %d", error));
    }

    @Override
    public void onResults(Bundle results) {
        Log.d(TAG, "results");
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.d(TAG, "partial results");
        ArrayList<String> matches = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.d(TAG, "event");
    }
}
