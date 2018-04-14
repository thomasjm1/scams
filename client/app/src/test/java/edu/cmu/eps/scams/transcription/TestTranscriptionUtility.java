package edu.cmu.eps.scams.transcription;

import android.util.Pair;

import org.junit.Test;

import java.io.File;
import java.util.Base64;


import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
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