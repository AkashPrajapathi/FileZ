package com.example.android.filez;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public final class MimeUtils {

    public static final String[] MIME_TYPES = {"image/*", "video/*", "audio/*", "text/plain"};
    public static final String[] IMG_FILE_FORMATS = {".png", ".jpg", ".jpeg", ".gif"};
    public static final String[] AUDIO_FILE_FORMATS = {".mp3", ".wav", ".m4a"};
    public static final String[] VIDEO_FILE_FORMATS = {".mp4", ".mkv", ".3gp"};

    @SuppressLint("QueryPermissionsNeeded")
    public static void sendIntent(String filePath, Context context) {

        String mimeType;
        if (checkFileType(IMG_FILE_FORMATS, filePath)) {
            mimeType = MIME_TYPES[0];
        } else if (checkFileType(VIDEO_FILE_FORMATS, filePath)) {
            mimeType = MIME_TYPES[1];
        } else if (checkFileType(AUDIO_FILE_FORMATS, filePath)) {
            mimeType = MIME_TYPES[2];
        } else {
            mimeType = MIME_TYPES[3];
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(filePath), mimeType);
        String title = "Open file...";
        Intent chooser = Intent.createChooser(intent, title);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(chooser);
        }
    }

    public static boolean checkFileType(String[] fileTypes, String path) {
        for (String file :
                fileTypes) {
            if (path.contains(file)) {
                return true;
            }
        }
        return false;
    }
}
