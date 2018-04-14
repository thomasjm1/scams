package edu.cmu.eps.scams.logic;

import java.util.TreeMap;

public class OutgoingMessage {

    private final TreeMap<String, Object> properties;

    private String recipient;

    public OutgoingMessage() {
        this.properties = new TreeMap<String, Object>();
    }

    public TreeMap<String, Object> getProperties() {
        return properties;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
