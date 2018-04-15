package edu.cmu.eps.scams.logic.model;

import org.json.JSONException;

public class NotifyMessageContent extends MessageContent {

    public NotifyMessageContent(String jsonText) throws JSONException {
        super(jsonText);
    }

    public String getTitle() throws JSONException {
        return this.jsonObject.getString("title");
    }

    public String getMessage() throws JSONException {
        return this.jsonObject.getString("message");
    }


}
