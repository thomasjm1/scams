package edu.cmu.eps.scams.transcription;

import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionAlternative;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResult;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;

import java.io.File;
import java.io.FileNotFoundException;

public class IbmTranscriptionUtility {

    public static TranscriptionResult transcribe(File file) throws FileNotFoundException {
        SpeechToText service = new SpeechToText();
        service.setUsernameAndPassword("bae01637-45a9-43bf-8237-c81450b3e863", "gEDb3lNiFTga");
        service.setEndPoint("https://stream.watsonplatform.net/speech-to-text/api");
        RecognizeOptions options = new RecognizeOptions.Builder()
                .contentType("audio/wav")
                .audio(file)
                .build();
        SpeechRecognitionResults results = service.recognize(options).execute();
        StringBuilder output = new StringBuilder();
        double confidence = 1.0;
        for (SpeechRecognitionResult result : results.getResults()) {
            for (SpeechRecognitionAlternative item : result.getAlternatives()) {
                output.append(item.getTranscript());
                confidence = confidence * item.getConfidence();
            }
        }
        return new TranscriptionResult(output.toString(), confidence);
    }
}
