package edu.cmu.eps.scams.logic;

import java.util.TreeMap;

public class OutgoingMessage {

    private final TreeMap<String, Object> properties;

    private final String recipient;

    public OutgoingMessage(String recipient) {
        this.properties = new TreeMap<String, Object>();
        this.recipient = recipient;
    }

    public TreeMap<String, Object> getProperties() {
        return properties;
    }

    public String getRecipient() {
        return recipient;
    }
}
