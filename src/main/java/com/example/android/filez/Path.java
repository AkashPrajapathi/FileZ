package com.example.android.filez;

import java.io.File;

public class Path {
    private final String mPath;

    public Path(String path){
        mPath = path;
    }

    public File getPath(){
        return new File(mPath);
    }

    public String getName(){
        File file = new File(mPath);
        return file.getName();
    }

    public int getItems(){
        File file = new File(mPath);
        int items = 0;
        if (file.isDirectory()){
            File[] dirItems = file.listFiles();
            assert dirItems != null;
            items = dirItems.length;
        }
        return items;
    }
}
