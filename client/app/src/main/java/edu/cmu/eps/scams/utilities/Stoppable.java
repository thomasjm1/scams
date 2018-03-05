package edu.cmu.eps.scams.utilities;

import android.util.Log;

/**
 * Created by thoma on 3/5/2018.
 */

public abstract class Stoppable implements Runnable {

    private static final String TAG = "Stoppable";

    private boolean stopFlag;

    public Stoppable() {
        this.stopFlag = false;
    }

    @Override
    public void run() {
        try {
            this.setup();
        } catch (RuntimeException exception) {
            Log.d(TAG, "Encountered runtime exception on setup");
            return;
        }
        while (this.isStopped() == false) {
            try {
                this.loop();
            } catch (InterruptedException exception) {
                Log.d(TAG, "Interrupted during loop");
            } catch (RuntimeException exception) {
                Log.d(TAG, "Loop threw exception, stopping thread");
            }
        }
        try {
            this.cleanup();
        } catch (RuntimeException exception) {
            Log.d(TAG, "Encountered runtime exception on cleanup");
        }

    }

    public void stop() {
        this.stopFlag = true;
    }

    private boolean isStopped() {
        return this.stopFlag;
    }

    protected abstract void setup();

    protected abstract void loop() throws InterruptedException;

    protected abstract void cleanup();

}
