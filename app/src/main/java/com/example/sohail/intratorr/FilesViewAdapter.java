package com.example.sohail.intratorr;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sohail on 29/9/17.
 */

public class FilesViewAdapter extends RecyclerView.Adapter<FilesViewAdapter.FilesViewHolder> implements Filterable {

    private ArrayList<FilesRow> filesList, mFilteredList;
    private ViewPager viewPager;



    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredList = filesList;
                } else {
                    ArrayList<FilesRow> filteredList = new ArrayList<>();
                    for (FilesRow files : filesList) {
                        if (files.getName().toLowerCase().contains(charString)) {
                            filteredList.add(files);
                        }
                    }
                    mFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<FilesRow>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class FilesViewHolder extends RecyclerView.ViewHolder {
        public TextView name, size, serial;
        public ImageView downloadView;

        public FilesViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.filename);
            size = view.findViewById(R.id.filesize);
            serial = view.findViewById(R.id.serial_no);
            downloadView = view.findViewById(R.id.download_file);
        }
    }

    public FilesViewAdapter(ArrayList<FilesRow> filesList, ViewPager viewPager) {
        this.filesList = filesList;
        mFilteredList = filesList;
        this.viewPager = viewPager;
    }

    @Override
    public FilesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filesrow, parent, false);

        return new FilesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FilesViewHolder holder, int position) {
        FilesRow file = mFilteredList.get(position);
        holder.name.setText(file.getName());
        holder.size.setText(file.getSize());
        holder.serial.setText(String.valueOf(position + 1));
        holder.downloadView.setVisibility(View.VISIBLE);
        holder.downloadView.setOnClickListener(view -> {
            viewPager.setCurrentItem(0);
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }
}
