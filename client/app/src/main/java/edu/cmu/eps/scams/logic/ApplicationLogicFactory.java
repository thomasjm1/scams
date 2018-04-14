package edu.cmu.eps.scams.logic;

import android.content.Context;

import edu.cmu.eps.scams.storage.LocalStorageFactory;

public class ApplicationLogicFactory {

    public static IApplicationLogic build(Context context) {
        return new ApplicationLogic(LocalStorageFactory.build(context));
    }
}
