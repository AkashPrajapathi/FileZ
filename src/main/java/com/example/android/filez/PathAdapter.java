package com.example.android.filez;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

public class PathAdapter extends RecyclerView.Adapter<PathAdapter.RPathAdapterViewHolder> {

    private List<Path> mPaths;

    public final RPathAdapterOnClickHandler mClickHandler;

    public interface RPathAdapterOnClickHandler{
        void onClick(File path);
    }

    public class RPathAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView iconImageView;
        public TextView nameTextView;
        public TextView itemsTextView;

        public TextView emptyMsgTextView;
        public RPathAdapterViewHolder(View view){
            super(view);
            iconImageView = view.findViewById(R.id.folder_image);
            nameTextView = view.findViewById(R.id.folder_text_view);
            itemsTextView = view.findViewById(R.id.items_text_view);
            emptyMsgTextView = view.findViewById(R.id.tv_empty_msg);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mClickHandler.onClick(mPaths.get(position).getPath());
        }
    }

    public PathAdapter(RPathAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public RPathAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.folder_list_item,parent,false);
        return new RPathAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RPathAdapterViewHolder holder, int position) {
        Path currentPath = mPaths.get(position);
        if(currentPath==null){
            holder.emptyMsgTextView.setVisibility(View.VISIBLE);
        }else {
            holder.emptyMsgTextView.setVisibility(View.INVISIBLE);
        }
        String strName;
        assert currentPath != null;
        String name = currentPath.getName();
        if (name.length() > 28) {
            strName = name.substring(0, 20) + "..." + name.substring(name.length() - 8);
        } else {
            strName = name;
        }
        holder.nameTextView.setText(strName);

        if (currentPath.getPath().isDirectory()) {
            String items = "Items " + currentPath.getItems();
            holder.itemsTextView.setText(items);
            holder.nameTextView.setGravity(Gravity.BOTTOM);
            holder.iconImageView.setBackgroundResource(R.drawable.baseline_folder_24);

        } else {
            holder.nameTextView.setGravity(Gravity.CENTER_VERTICAL);
            String fileSize = "size " + getReasonableFileSize(currentPath.getPath());
            holder.itemsTextView.setText(fileSize);
            if (MimeUtils.checkFileType(MimeUtils.IMG_FILE_FORMATS, currentPath.getName())) {
                Drawable drawable = Drawable.createFromPath(currentPath.getPath().getAbsolutePath());
                holder.iconImageView.setBackground(drawable);
            } else if (MimeUtils.checkFileType(MimeUtils.VIDEO_FILE_FORMATS, currentPath.getName())) {
                holder.iconImageView.setBackgroundResource(R.drawable.baseline_movie_24);
            } else if (MimeUtils.checkFileType(MimeUtils.AUDIO_FILE_FORMATS, currentPath.getName())) {
                holder.iconImageView.setBackgroundResource(R.drawable.baseline_audio_file_24);
            } else {
                holder.iconImageView.setBackgroundResource(R.drawable.baseline_description_24);
            }

        }
    }

    @Override
    public int getItemCount() {
        if (mPaths==null) return 0;
        return mPaths.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPaths(List<Path> paths){
        mPaths = paths;
        notifyDataSetChanged();
    }

    private String getReasonableFileSize(File path) {
        if (path.isDirectory()) {
            return "";
        }
        long size = path.length();
        double kb = (double) (size / 1024);
        double mb = (double)size / 1024 / 1024;
        double gb = (double)size / 1024 / 1024 / 1024;
        if (mb > 900) {
            return formatAtTwoDecimalPlace(gb) + "GB";
        }
        if (kb > 900) {
            return formatAtTwoDecimalPlace(mb) + "MB";
        }
        if (size > 900) {
            return formatAtTwoDecimalPlace(kb) + "KB";
        }

        return size + "B";
    }
    private String formatAtTwoDecimalPlace(Double value) {
        return new DecimalFormat("0.00").format(value);
    }

}
