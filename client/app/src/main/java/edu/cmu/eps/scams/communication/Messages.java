package edu.cmu.eps.scams.communication;

import java.time.Instant;
import org.json.*;
import edu.cmu.eps.scams.utilities.HTTPAction;


public class Messages{
    private String recipient_;
    private String content_;
    private String created;
    private String accessToken_;
    private String url;

    public Messages(String recipient, String content, String accessToken){
        recipient_ = recipient;
        content_ = content;
        created = GetTime();
        accessToken_ = accessToken;
        url = "https://eps-scams.appspot.com/api/messages/";
    }

    public JSONObject Create() throws Exception{
        JSONObject data = MakeJson();
        HTTPAction action = new HTTPAction(url, "application/json");
        JSONObject response = action.PostData(data, accessToken_);
        return response;
    }

    public JSONObject Retrieve() throws Exception{
        JSONObject data = MakeJson();
        HTTPAction action = new HTTPAction(url, "application/json");
        JSONObject response = action.GetData(data, accessToken_);
        return response;
    }

    public JSONObject Update(String accessToken_) throws Exception{
        JSONObject data = MakeJson();
        HTTPAction action = new HTTPAction(url, "application/json");
        JSONObject response = action.PutData(data, accessToken_);
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