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


public class HttpAction {
    private URL urlObj;
    private HttpURLConnection conn;


    public HttpAction(String url) throws Exception{
        urlObj = new URL(url);
        conn = (HttpURLConnection)urlObj.openConnection();
    }


    /**
     * GET method
     * @return
     * @throws Exception
     */
    public JSONObject GetData() throws Exception{

        conn.setRequestMethod("GET");
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

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );

        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null){
            sb.append(line);
        }
        JSONObject res = new JSONObject(sb.toString());
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
