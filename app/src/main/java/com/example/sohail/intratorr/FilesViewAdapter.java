package com.example.sohail.intratorr;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sohail on 29/9/17.
 */

public class FilesViewAdapter extends RecyclerView.Adapter<FilesViewAdapter.FilesViewHolder>{

    private ArrayList<FilesRow> filesList;

    public class FilesViewHolder extends RecyclerView.ViewHolder {
        public TextView name, size;

        public FilesViewHolder(View view) {
            super(view);
            name =  view.findViewById(R.id.filename);
            size =  view.findViewById(R.id.filesize);
        }
    }
    public FilesViewAdapter(ArrayList<FilesRow> filesList) {
        this.filesList = filesList;
    }

    @Override
    public FilesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filesrow, parent, false);

        return new FilesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FilesViewHolder holder, int position) {
        FilesRow movie = filesList.get(position);
        holder.name.setText(movie.getName());
        holder.size.setText(movie.getSize());
    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }

}
