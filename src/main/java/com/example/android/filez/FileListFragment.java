package com.example.android.filez;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class FileListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Path>> , PathAdapter.RPathAdapterOnClickHandler {

    File mFilePath;

    Context mContext;
    private static final int LOADER_ID = 1;
    private PathAdapter mAdapter;

    private ProgressBar mLoadingIndicator;

    public FileListFragment(Context context, File file) {
        mContext = context;
        mFilePath = file;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_list, container, false);

        mAdapter = new PathAdapter(this);
        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerview_files);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(container.getContext(),LinearLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mAdapter);
        mLoadingIndicator = view.findViewById(R.id.pb_loading_indicator);
        androidx.loader.app.LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(LOADER_ID,null,this);
        return view;
    }

    @NonNull
    @Override
    public Loader<List<Path>> onCreateLoader(int id, @Nullable Bundle args) {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        return new FileLoader(requireContext(), mFilePath);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Path>> loader, List<Path> paths) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (paths!=null){
            mAdapter.setPaths(paths);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Path>> loader) {
        mAdapter.setPaths(null);
    }

    @Override
    public void onClick(File path) {
        if (path.isDirectory()) {
            FileListFragment fg = new FileListFragment(mContext, path);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction().replace(R.id.container, fg).addToBackStack(null).commit();
        } else {
            MimeUtils.sendIntent(path.getAbsolutePath(), requireContext());
        }
    }
}