package edu.cmu.eps.scams.recordings;

import edu.cmu.eps.scams.files.IOutputFileFactory;
import edu.cmu.eps.scams.utilities.IRunnableFactory;

/**
 * Created by thoma on 3/5/2018.
 * This class builds recording objects to record and write to file.
 */

public class RecordingStoppableFactory implements IRunnableFactory {

    private final IOutputFileFactory outputFileFactory;

    public RecordingStoppableFactory(IOutputFileFactory outputFileFactory) {
        this.outputFileFactory = outputFileFactory;
    }

    @Override
    public Runnable build() {
        return new RecordingStoppable(this.outputFileFactory.build());
    }
}
