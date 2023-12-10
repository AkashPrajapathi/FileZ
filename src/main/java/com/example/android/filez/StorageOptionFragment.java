package com.example.android.filez;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;

public class StorageOptionFragment extends Fragment {

    File[] mStoragePath;
    Context mContext;
    public StorageOptionFragment(Context context, File[] storagePath) {
        mContext = context;
        mStoragePath = storagePath;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_storage_option, container, false);
        Button deviceStorageBtn = view.findViewById(R.id.btn_device_storage);
        Button sdcardBtn = view.findViewById(R.id.btn_sdcard);
        deviceStorageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strPath = mStoragePath[1]+"/0";
                File path = new File(strPath);
                requireActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.container, new FileListFragment(mContext, path)).addToBackStack(null).commit();
            }
        });

        sdcardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File path = new File(mStoragePath[0].getAbsolutePath());
                requireActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.container, new FileListFragment(mContext, path)).addToBackStack(null).commit();
            }
        });
        return view;
    }
}