package com.example.android.filez;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE = 32;
    File[] mStorageOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkPermission()) {
            requestPermission();
        }
        // Getting all available device external storage
        mStorageOptions = new File(Objects.requireNonNull(
                new File(Objects.requireNonNull(
                        Environment.getExternalStorageDirectory()
                                .getParent())).getParent())).listFiles();
        getSupportFragmentManager().beginTransaction().
                add(R.id.container, new StorageOptionFragment(this, mStorageOptions)).commit();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private boolean checkPermission() {
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return false;
        }

        int result = ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "File permission requires!",
                    Toast.LENGTH_SHORT).show();
        }
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQ_CODE);
    }

}