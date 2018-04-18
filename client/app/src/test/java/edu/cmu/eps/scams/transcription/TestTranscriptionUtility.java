package edu.cmu.eps.scams.transcription;

import android.util.Pair;

import org.junit.Test;

import java.io.File;
import java.util.Base64;


import static org.junit.Assert.*;

/**
 * Test the Google API transcription. Usually fails because it needs a valid token
 */
public class TestTranscriptionUtility {
    @Test
    public void testTranscribe() throws Exception, TranscriptionException {
        File file = new File("test.wav");
        byte[] content = TranscriptionUtility.readFile(file);
        String contentString = Base64.getEncoder().encodeToString(content);
            try {
            TranscriptionResult results = TranscriptionUtility.transcribe("LINEAR16", 44100, contentString);
            assertTrue(results.getText().length() > 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}