package edu.cmu.eps.scams.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thoma on 4/13/2018.
 */

public class History {

    public String Time;
    public String PhoneNumber;

    public History(String PhoneNumber, String Time) {
        this.Time = Time;
        this.PhoneNumber = PhoneNumber;
    }

    public String getTimeOfCall() {
        return this.Time;
    }

    public String getPhoneNumber() {
        return this.PhoneNumber;

    }
}



