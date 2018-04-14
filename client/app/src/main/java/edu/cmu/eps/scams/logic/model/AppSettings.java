package edu.cmu.eps.scams.logic.model;

/**
 * Created by thoma on 4/13/2018.
 */

public class AppSettings {

    private final String identifier;
    private final boolean registered;
    private final String secret;
    private final String profile;
    private final String recovery;

    public AppSettings(String identifier, boolean registered, String secret, String profile, String recovery) {
        this.identifier = identifier;
        this.registered = registered;
        this.secret = secret;
        this.profile = profile;
        this.recovery = recovery;
    }

    public String getIdentifier() {
        return identifier;
    }

    public boolean isRegistered() {
        return registered;
    }

    public String getSecret() {
        return secret;
    }

    public String getProfile() {
        return profile;
    }

    public String getRecovery() {
        return recovery;
    }

    public static AppSettings defaults() {
        return null;
    }
}
