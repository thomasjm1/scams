package edu.cmu.eps.scams.communication;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;
import edu.cmu.eps.scams.utilities.HTTPAction;


public class Login{
    private String identifer_;
    private String secret_;
    private String url;

    public Login(String identifer, String secret){
        identifer_ = identifer;
        secret_ = secret;
        url = "https://eps-scams.appspot.com/api/authentication/login";
    }

    public JSONObject send() throws Exception{
        HTTPAction action = new HTTPAction(url);
        JSONObject data = new JSONObject();
        data.put("identifier", identifer_);
        data.put("secret", secret_);
        JSONObject response = action.PostData(data);
        return response;
    }
}