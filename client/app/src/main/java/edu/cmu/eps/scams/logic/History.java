package edu.cmu.eps.scams.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thoma on 4/13/2018.
 */

public class History {

    private int id;
    public String Time;
    public String PhoneNumber;

    public History(String PhoneNumber, String Time) {
        this.Time = Time;
        this.PhoneNumber = PhoneNumber;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimeOfCall() {
        return this.Time;
    }

    public String getPhoneNumber() {
        return this.PhoneNumber;

    }
}



