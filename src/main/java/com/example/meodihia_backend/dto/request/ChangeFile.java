package com.example.meodihia_backend.dto.request;

public class ChangeFile {
    private String file;

    public ChangeFile(String file) {
        this.file = file;
    }

    public ChangeFile() {
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
