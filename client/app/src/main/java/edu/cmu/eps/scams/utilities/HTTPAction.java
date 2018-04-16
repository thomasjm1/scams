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

    private URL urlObj;
    private HttpURLConnection conn;
    private String contentType_;


    public HTTPAction(String url, String contentType) throws Exception{
        urlObj = new URL(url);
        conn = (HttpURLConnection)urlObj.openConnection();
        contentType_ = contentType;

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
    public JSONObject GetData(JSONObject jsonObject) throws Exception{

        conn.setRequestMethod("GET");
        conn.setRequestProperty("content_type", contentType_);
        return ReadResponse(conn);
    }

    public JSONObject GetData(JSONObject jsonObject, String accessToken_) throws Exception{

        conn.setRequestMethod("GET");
        conn.setRequestProperty("content_type", contentType_);
        conn.setRequestProperty("Authorization", "Bearer " + accessToken_);
        return ReadResponse(conn);
    }


    /**
     * POST method
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public JSONObject PostData(JSONObject jsonObject) throws Exception{

        conn.setRequestMethod("POST");
        conn.setRequestProperty("content_type", contentType_);

        WriteInput(conn, jsonObject);
        return ReadResponse(conn);
    }

    public JSONObject PostData(JSONObject jsonObject, String accessToken_) throws Exception{

        conn.setRequestMethod("POST");
        conn.setRequestProperty("content_type", contentType_);
        conn.setRequestProperty("Authorization", "Bearer " + accessToken_);

        WriteInput(conn, jsonObject);
        return ReadResponse(conn);
    }

    /**
     * PUT method
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public JSONObject PutData(JSONObject jsonObject) throws Exception{

        conn.setRequestMethod("PUT");
        conn.setRequestProperty("content_type", contentType_);

        WriteInput(conn, jsonObject);
        return ReadResponse(conn);
    }


    public JSONObject PutData(JSONObject jsonObject, String accessToken_) throws Exception{

        conn.setRequestMethod("PUT");
        conn.setRequestProperty("content_type", contentType_);
        conn.setRequestProperty("Authorization", "Bearer " + accessToken_);

        WriteInput(conn, jsonObject);
        return ReadResponse(conn);
    }


    /**
     * Read the response from the server.
     * @param conn
     * @return
     * @throws Exception
     */
    private JSONObject ReadResponse(HttpURLConnection conn) throws Exception{

        int status = conn.getResponseCode();
        String Authorization = "";

        if (status == HttpURLConnection.HTTP_OK) {
            Authorization = conn.getHeaderField("Authorization");
            if (Authorization == null) {
                Authorization = "";
            }else {
                Authorization = Authorization.split(" ")[1]; // get the token
            }
            System.out.println(Authorization);
        } else {
            BufferedReader err = new BufferedReader(
                    new InputStreamReader(conn.getErrorStream())
            );
            String line;
            while ((line = err.readLine()) != null) {
                System.out.println(line);
            }
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
					       );
        StringBuilder stringBuffer = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }
        JSONObject res = new JSONObject(sb.toString());
        res.put("token", Authorization);
        return res;
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
