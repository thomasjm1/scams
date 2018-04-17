package edu.cmu.eps.scams.communication;

import org.json.*;
import edu.cmu.eps.scams.utilities.HTTPAction;


public class Register{
    private String identifer_;
    private String secret_;
    private String profile_;
    private String recovery_;
    private String url;

    public Register(String identifer, String secret, String profile, String recovery) {
        identifer_ = identifer;
        secret_ = secret;
        profile_ = profile;
        recovery_ = recovery;
        url = "https://eps-scams.appspot.com/api/authentication/register";
    }

    public JSONObject send() throws Exception{
        HTTPAction action = new HTTPAction(url, "application/json");
        JSONObject data = new JSONObject();
        data.put("identifier", identifer_);
        data.put("secret", secret_);
        data.put("profile", profile_);
        data.put("recovery", recovery_);
        JSONObject response = action.postRequest(data);
        return response;
    }
}