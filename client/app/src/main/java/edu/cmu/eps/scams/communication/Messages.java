package edu.cmu.eps.scams.communication;

import java.sql.Timestamp;
import java.time.Instant;
import org.json.*;
import edu.cmu.eps.scams.utilities.HTTPAction;
import edu.cmu.eps.scams.utilities.TimestampUtility;


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
        JSONObject response = action.postRequest(data, accessToken_);
        return response;
    }

    public JSONObject Retrieve() throws Exception{
        JSONObject data = MakeJson();
        HTTPAction action = new HTTPAction(url, "application/json");
        JSONObject response = action.getRequest(data, accessToken_);
        return response;
    }

    public JSONObject Update(String accessToken_) throws Exception{
        JSONObject data = MakeJson();
        HTTPAction action = new HTTPAction(url, "application/json");
        JSONObject response = action.putRequest(data, accessToken_);
        return response;
    }

    private String GetTime() {
        return String.valueOf(TimestampUtility.now());
    }

    private JSONObject MakeJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("recipient", recipient_);
        obj.put("content", content_);
        obj.put("created", created);
        return obj;
    }
}
