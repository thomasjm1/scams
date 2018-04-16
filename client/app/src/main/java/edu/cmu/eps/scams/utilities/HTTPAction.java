package edu.cmu.eps.scams.utilities;
/**
 * Created by Ao Chen on 4/3/18.
 *
 * This is a utility class that can be used to send http requests to server.
 * There are three methods: GET, POST and PUT.
 *
 * To use this class, you need add extra lib "org.json", which is available at
 * "https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.json%22%20AND%20a%3A%22json%22"
 */
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;


public class HTTPAction {
    private URL urlObj;
    private HttpURLConnection conn;
    private String contentType_;


    public HTTPAction(String url, String contentType) throws Exception{
        urlObj = new URL(url);
        conn = (HttpURLConnection)urlObj.openConnection();
        contentType_ = contentType;
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
            Authorization = Authorization.split(" ")[1]; // get the token
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

        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null){
            sb.append(line);
        }
        JSONObject res = new JSONObject(sb.toString());
        res.put("token", Authorization);
        return res;
    }


    /**
     * Send input data to server.
     * @param conn
     * @param jsonObject
     * @throws Exception
     */
    private void WriteInput(HttpURLConnection conn, JSONObject jsonObject) throws Exception{
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);
        conn.setDoInput(true);

        String json = jsonObject.toString();

        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes("UTF-8"));
        os.close();
    }
}
