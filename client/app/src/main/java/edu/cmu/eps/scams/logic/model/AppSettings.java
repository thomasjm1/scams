package edu.cmu.eps.scams.logic.model;

import android.util.Base64;

import java.security.SecureRandom;

/**
 * Created by thoma on 4/13/2018.
 */

public class AppSettings {

    private static final int IDENTIFIER_BYTES = 256;
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
        SecureRandom random = new SecureRandom();
        byte identifierBytes[] = new byte[IDENTIFIER_BYTES];
        random.nextBytes(identifierBytes);

        byte secretBytes[] = new byte[IDENTIFIER_BYTES];
        random.nextBytes(secretBytes);

        return new AppSettings(
                android.util.Base64.encodeToString(identifierBytes, Base64.NO_WRAP),
                false,
                android.util.Base64.encodeToString(secretBytes, Base64.NO_WRAP),
                "{}",
                "{}"
        );
    }
}