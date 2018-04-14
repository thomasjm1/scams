package edu.cmu.eps.scams.logic.model;

/**
 * Created by thoma on 4/13/2018.
 */

public class AppSettings {

    private final String identifier;

    public AppSettings(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static AppSettings defaults() {
        return null;
    }
}
