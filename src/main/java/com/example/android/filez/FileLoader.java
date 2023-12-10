package com.example.android.filez;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FileLoader extends AsyncTaskLoader<List<Path>> {

    File mFilePath;
    public FileLoader(@NonNull Context context,File path) {
        super(context);
        mFilePath = path;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Path> loadInBackground() {
        ArrayList<Path> paths = new ArrayList<>();
        FilenameFilter filter = (file, name) -> !name.startsWith(".");
        File[] dirFiles = mFilePath.listFiles(filter);

        assert dirFiles != null;
        if (dirFiles.length > 1) {
            Arrays.sort(dirFiles, Comparator.comparing(file -> file.getName().toLowerCase()));
            Arrays.sort(dirFiles, (file, t1) -> {
                if (file.isDirectory() == t1.isDirectory()) {
                    return 0;
                } else if (file.isDirectory() && !t1.isDirectory()) {
                    return -1;
                } else {
                    return 1;
                }
            });
        }

        for (File dirFile : dirFiles) {
            paths.add(new Path(dirFile.getAbsolutePath()));
        }
        return paths;
    }
}
