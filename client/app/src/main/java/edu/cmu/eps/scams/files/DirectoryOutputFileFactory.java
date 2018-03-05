package edu.cmu.eps.scams.files;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by thoma on 3/5/2018.
 */

public class DirectoryOutputFileFactory implements IOutputFileFactory {

    private final File appDirectory;
    private final String subDirectoryName;
    private final File directory;

    public DirectoryOutputFileFactory(File appDirectory, String subDirectoryName) {
        this.appDirectory = appDirectory;
        this.subDirectoryName = subDirectoryName;
        this.directory = new File(this.appDirectory.getAbsolutePath(), this.subDirectoryName);
    }

    @Override
    public File build() {
        return null;
    }
}
