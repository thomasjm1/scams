package edu.cmu.eps.scams.logic;

/**
 * Created by thoma on 4/13/2018.
 */

public class Association {

    private final String identifier;

    public Association(String qrValue) {
        this.identifier = qrValue;
    }

    public String getIdentifier() {
        return identifier;
    }
}
