package edu.cmu.eps.scams.recordings;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import edu.cmu.eps.scams.files.DirectoryOutputFileFactory;
import edu.cmu.eps.scams.utilities.RunnableService;

public class RecordingService extends Service {

    private static final String RECORDINGS_DIRECTORY = "recordings";
    private final RunnableService runnableService;

    public RecordingService() {
        this.runnableService = new RunnableService(
                new RecordingStoppableFactory(
                        new DirectoryOutputFileFactory(
                                this.getApplicationContext().getFilesDir(),
                                RECORDINGS_DIRECTORY
                        )
                )
        );
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
