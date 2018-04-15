package edu.cmu.eps.scams.communication;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import org.json.*;
import edu.cmu.eps.scams.utilities.HTTPAction;


public class Messages{
    private String recipient_;
    private String content_;
    private String created;
    private String url;

    public Messages(String recipient, String content){
        recipient_ = recipient;
        content_ = content;
        created = GetTime();
        url = "https://eps-scams.appspot.com/api/messages/";
    }

    public JSONObject Create() throws Exception{
        JSONObject data = MakeJson();
        HTTPAction action = new HTTPAction(url);
        JSONObject response = action.PostData(data);
        return response;
    }

    public JSONObject Retrieve() throws Exception{
        JSONObject data = MakeJson();
        HTTPAction action = new HTTPAction(url);
        JSONObject response = action.GetData(data);
        return response;
    }

    public JSONObject Update() throws Exception{
        JSONObject data = MakeJson();
        HTTPAction action = new HTTPAction(url);
        JSONObject response = action.PutData(data);
        return response;
    }

    private String GetTime() {
        return String.valueOf(Instant.now().toEpochMilli());
    }

    private JSONObject MakeJson() {
        JSONObject obj = new JSONObject();
        obj.put("recipient", recipient_);
        obj.put("content", content_);
        obj.put("created", created);
        return obj;
    }
}