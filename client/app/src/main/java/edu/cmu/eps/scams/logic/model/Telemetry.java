package edu.cmu.eps.scams.logic.model;

import java.util.Map;
import java.util.TreeMap;

public class Telemetry {
    private final TreeMap<String, Object> properties;
    private final String dataType;
    private final String content;
    private final long created;

    public Telemetry(String dataType, String content, long created) {
        this.dataType = dataType;
        this.content = content;
        this.created = created;
        this.properties = new TreeMap<String, Object>();
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public String getDataType() {
        return dataType;
    }

    public String getContent() {
        return content;
    }

    public long getCreated() {
        return created;
    }
}
