package edu.cmu.eps.scams.utilities;

import java.util.Date;

public class TimestampUtility {

    public static long now() {
        return new Date().getTime();
    }

    public static String format(long timestamp) {
        return new Date(timestamp).toString();
    }
}
