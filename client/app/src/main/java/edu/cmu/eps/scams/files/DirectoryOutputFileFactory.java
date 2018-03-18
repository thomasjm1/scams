package edu.cmu.eps.scams.files;

import android.util.Log;

import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Created by thoma on 3/5/2018.
 * This class gets new files in local storage for recordings.
 */

public class DirectoryOutputFileFactory implements IOutputFileFactory {

    private static final String TAG = "DirectoryOutputFileFactory";
    private final File appDirectory;
    private final String subDirectoryName;
    private final File directory;
    private final String extension;

    public DirectoryOutputFileFactory(File appDirectory, String subDirectoryName) {
        this.appDirectory = appDirectory;
        this.subDirectoryName = subDirectoryName;
        this.directory = new File(this.appDirectory.getAbsolutePath(), this.subDirectoryName);
        if (this.directory.exists() == false) {
            Log.d(TAG, String.format("Building directories for %s", this.directory.getAbsolutePath()));
            this.directory.mkdirs();
        }
        this.extension = "wav";
    }

    @Override
    public File build() {
        File result = new File(this.appDirectory.getAbsolutePath() + "/" + this.subDirectoryName + "/" + this.buildFilename());
        Log.d(TAG, String.format("Building file %s", result.getAbsolutePath()));
        return result;
    }

    private String buildFilename() {
        return String.format("%s.%s", UUID.randomUUID().toString().replace("-", ""), this.extension);
    }
}
