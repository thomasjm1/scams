package edu.cmu.eps.scams.files;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by thoma on 3/17/2018.
 */

public class OutputStreamMap {

    private final TreeMap<String, OutputStream> map;

    public OutputStreamMap() {
        this.map = new TreeMap<>();
    }

    public void add(String key, OutputStream value) {
        this.map.put(key, value);
    }

    public OutputStream remove(String name) {
        return this.map.remove(name);
    }

    public Map<String, Boolean> write(byte[] data) {
        Map<String, Boolean> results = new TreeMap<>();
        for (Map.Entry<String, OutputStream> item : this.map.entrySet()) {
            try {
                item.getValue().write(data);
                results.put(item.getKey(), true);
            } catch (IOException exception) {
                results.put(item.getKey(), false);
            }
        }
        return results;
    }
}
