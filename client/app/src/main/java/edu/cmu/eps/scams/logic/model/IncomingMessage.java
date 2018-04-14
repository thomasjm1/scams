package edu.cmu.eps.scams.logic.model;

public class IncomingMessage {

    private final String identifier;
    private final String sender;
    private final String recipient;
    private final int state;
    private final Long created;
    private final Long received;
    private final Long recipientReceived;

    public IncomingMessage(String identifier, String sender, String recipient, int state, Long created, Long received, Long recipientReceived) {
        this.identifier = identifier;
        this.sender = sender;
        this.recipient = recipient;
        this.state = state;
        this.created = created;
        this.received = received;
        this.recipientReceived = recipientReceived;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public int getState() {
        return state;
    }

    public Long getCreated() {
        return created;
    }

    public Long getReceived() {
        return received;
    }

    public Long getRecipientReceived() {
        return recipientReceived;
    }
}
