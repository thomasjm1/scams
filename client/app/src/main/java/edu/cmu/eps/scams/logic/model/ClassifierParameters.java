package edu.cmu.eps.scams.logic.model;

public class ClassifierParameters {
    private final String content;

    public ClassifierParameters(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public static ClassifierParameters defaults() {
        return null;
    }
}
