package edu.cmu.eps.scams.transcription;

import android.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

import static android.util.Base64.DEFAULT;

/**
 * Created by thoma on 3/19/2018.
 */

public class TranscriptionUtility {
    public static Pair<String, Double> transcribe(File file) throws Exception {
        byte[] data = TranscriptionUtility.readFile(file);
        String dataString = android.util.Base64.encodeToString(data, DEFAULT);
        String postString = "{\n" +
                "  'config': {\n" +
                "    'encoding': 'LINEAR16',\n" +
                "    'sampleRateHertz': 16000,\n" +
                "    'languageCode': 'en-US',\n" +
                "    'enableWordTimeOffsets': false\n" +
                "  },\n" +
                "  'audio': {\n" +
                String.format("    'content': '%s'\n", dataString) +
                "  }";
        byte[] postData = postString.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        String request = "https://speech.googleapis.com/v1/speech:recognize";
        URL url = new URL(request);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "bearer: REPLACE ME");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("charset", "utf-8");
        connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        connection.setUseCaches(false);
        try (DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream())) {
            dataOutputStream.write(postData);
        }
        StringBuilder results = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                results.append(line);
            }
        }
        String result = results.toString();
        JSONObject rootObject = new JSONObject(result);
        JSONArray resultsArray = rootObject.getJSONArray("results");
        JSONObject resultItem = resultsArray.getJSONObject(0);
        JSONArray alternativesArray = resultItem.getJSONArray("alternatives");
        JSONObject alternativeItem = alternativesArray.getJSONObject(0);
        String text = alternativeItem.getString("transcript");
        double confidence = alternativeItem.getDouble("confidence");
        return new Pair<>(text, confidence);
    }

    private static byte[] readFile(File file) throws IOException {
        int size = (int) file.length();
        byte[] buffer = new byte[size];
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            fileInputStream.read(buffer, 0, size);
        }
        return buffer;

    }
}
