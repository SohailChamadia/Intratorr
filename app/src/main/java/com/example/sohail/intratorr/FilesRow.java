package com.example.sohail.intratorr;

import android.widget.TextView;

/**
 * Created by sohail on 28/9/17.
 */

public class FilesRow {
    private String name, size, path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FilesRow(String name, String path, String size) {
        this.name = name;
        this.size = size;
        this.path = path;

    }


}
