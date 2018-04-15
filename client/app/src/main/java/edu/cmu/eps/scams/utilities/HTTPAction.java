package edu.cmu.eps.scams.utilities;
/**
 * Created by Ao Chen on 4/3/18.
 * <p>
 * This is a utility class that can be used to send http requests to server.
 * There are three methods: GET, POST and PUT.
 * <p>
 * To use this class, you need add extra lib "org.json", which is available at
 * "https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.json%22%20AND%20a%3A%22json%22"
 */

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.*;

import javax.net.ssl.HttpsURLConnection;


public class HTTPAction {
    private HttpsURLConnection httpURLConnection;
    private String token;

    public HTTPAction(String url) throws Exception {
        URL urlObject = new URL(url);
        httpURLConnection = (HttpsURLConnection) urlObject.openConnection();
        this.token = null;
    }

    public void setToken(String token) {
        this.token = token;
        this.httpURLConnection.setRequestProperty("Authorization", String.format("Bearer %s", this.token));
    }

    /**
     * GET method
     * @return
     * @throws Exception
     */
    public JSONObject getRequest() throws Exception {
        this.httpURLConnection.setRequestMethod("GET");
        return ReadResponse(this.httpURLConnection);
    }


    /**
     * POST method
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public JSONObject postRequest(JSONObject jsonObject) throws Exception {
        this.httpURLConnection.setRequestMethod("POST");
        WriteInput(this.httpURLConnection, jsonObject);
        return ReadResponse(this.httpURLConnection);
    }

    /**
     * PUT method
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public JSONObject putRequest(JSONObject jsonObject) throws Exception {
        this.httpURLConnection.setRequestMethod("PUT");
        WriteInput(this.httpURLConnection, jsonObject);
        return ReadResponse(this.httpURLConnection);
    }


    /**
     * Read the response from the server.
     * @param conn
     * @return
     * @throws Exception
     */
    private JSONObject ReadResponse(HttpURLConnection conn) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );
        StringBuilder stringBuffer = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }
        return new JSONObject(stringBuffer.toString());
    }


    /**
     * Send input data to server.
     * @param connection
     * @param jsonObject
     * @throws Exception
     */
    private void WriteInput(HttpURLConnection connection, JSONObject jsonObject) throws Exception {
        String json = jsonObject.toString();
        byte[] postData = json.getBytes(StandardCharsets.UTF_8);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("charset", "utf-8");
        connection.setRequestProperty("Content-Length", Integer.toString(postData.length));
        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(postData);
        outputStream.close();
    }
}
