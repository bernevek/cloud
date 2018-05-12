package com.bernevek.trim11;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;

public class FileInfo implements Serializable {

    private static final long serialVersionUID = 229L;

    private byte[] fileContent;
    private String filename;

    public FileInfo(File file) throws IOException {
        fileContent = Files.readAllBytes(file.toPath());
        filename = file.getName();
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}

