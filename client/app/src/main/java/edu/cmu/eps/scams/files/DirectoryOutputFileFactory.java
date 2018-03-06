package edu.cmu.eps.scams.files;

import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Created by thoma on 3/5/2018.
 * This class gets new files in local storage for recordings.
 */

public class DirectoryOutputFileFactory implements IOutputFileFactory {

    private final File appDirectory;
    private final String subDirectoryName;
    private final File directory;

    public DirectoryOutputFileFactory(File appDirectory, String subDirectoryName) {
        this.appDirectory = appDirectory;
        this.subDirectoryName = subDirectoryName;
        this.directory = new File(this.appDirectory.getAbsolutePath(), this.subDirectoryName);
        if (this.directory.exists() == false) {
            this.directory.mkdir();
        }
    }

    @Override
    public File build() {
        return new File(this.directory.getAbsolutePath() + "/" + this.buildFilename());
    }

    private String buildFilename() {
        return String.format("%s.rec", UUID.randomUUID().toString().replace("-", ""));
    }
}
