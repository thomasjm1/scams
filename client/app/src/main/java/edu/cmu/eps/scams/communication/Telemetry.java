package edu.cmu.eps.scams.communication;


import java.time.Instant;
import org.json.*;
import edu.cmu.eps.scams.utilities.HTTPAction;
import edu.cmu.eps.scams.utilities.TimestampUtility;


public class Telemetry {
    private String dataType_;
    private String content_;
    private String created;
    private String accessToken_;
    private String url;

    public Telemetry(String dataType, String content, String accessToken) {
        dataType_ = dataType;
        content_ = content;
        accessToken_ = accessToken;
        created = GetTime();
        url = "https://eps-scams.appspot.com/api/telemetry/";
    }

    public JSONObject create() throws Exception{
        JSONObject obj = MakeJson();
        HTTPAction action = new HTTPAction(url, "application/json");
        JSONObject response = action.postRequest(obj, accessToken_);
        return response;
    }

    private String GetTime() {
        return String.valueOf(TimestampUtility.now());
    }

    private JSONObject MakeJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("data_type", dataType_);
        obj.put("content", content_);
        obj.put("created", created);
        return obj;
    }
}