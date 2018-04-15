package edu.cmu.eps.scams.communication;


import java.time.Instant;
import org.json.*;
import edu.cmu.eps.scams.utilities.HTTPAction;


public class Telemetry {
    private String dataType_;
    private String content_;
    private String created;
    private String url;

    public Telemetry(String dataType, String content) {
        dataType_ = dataType;
        content_ = content;
        created = GetTime();
        url = "https://eps-scams.appspot.com/api/telemetry/";
    }

    public JSONObject create() throws Exception{
        JSONObject obj = MakeJson();
        HTTPAction action = new HTTPAction(url);
        JSONObject response = action.PostData(obj);
        return response;
    }

    private String GetTime() {
        return String.valueOf(Instant.now().toEpochMilli());
    }

    private JSONObject MakeJson() {
        JSONObject obj = new JSONObject();
        obj.put("data_type", dataType_);
        obj.put("content", content_);
        obj.put("created", created);
        return obj;
    }
}