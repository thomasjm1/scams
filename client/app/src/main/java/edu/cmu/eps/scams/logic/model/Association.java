package edu.cmu.eps.scams.logic.model;

/**
 * Created by thoma on 4/13/2018.
 */

public class Association {

    private final String identifier;
    private final String name;

    public Association(String name, String qrValue) {
        this.identifier = qrValue;
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }
}
