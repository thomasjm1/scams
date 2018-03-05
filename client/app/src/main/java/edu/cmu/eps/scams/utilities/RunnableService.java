package edu.cmu.eps.scams.utilities;

import android.util.Log;

public class RunnableService {

    private static final String TAG = "RunnableService";

    private final IRunnableFactory runnableFactory;
    private Thread thread;

    public RunnableService(IRunnableFactory runnableFactory) {
        this.runnableFactory = runnableFactory;
        this.thread = null;
    }

    protected void start() {
        if (this.thread == null) {
            this.thread = new Thread(this.runnableFactory.build());
            this.thread.start();
        } else {
            Log.d(TAG, "Recording thread already running");
        }
    }

    protected void stop() {
        if (this.thread == null) {
            Log.d(TAG,"Recording thread already stopped");
        } else {
            this.thread.interrupt();
        }
    }
}
