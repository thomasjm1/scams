package edu.cmu.eps.scams.logic;

import java.util.TreeMap;

public class OutgoingMessage {

    private final TreeMap<String, Object> properties;

    public OutgoingMessage() {
        this.properties = new TreeMap<String, Object>();
    }

    public TreeMap<String, Object> getProperties() {
        return properties;
    }
}
