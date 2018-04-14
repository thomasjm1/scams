package edu.cmu.eps.scams.logic;

import java.util.TreeMap;

public class Telemetry {
    private final TreeMap<String, Object> properties;

    public Telemetry() {
        this.properties = new TreeMap<String, Object>();
    }

    public TreeMap<String, Object> getProperties() {
        return properties;
    }
}
