package com.example.inquallity.findartwork.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inquallity.findartwork.R;
import com.example.inquallity.findartwork.model.Album;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Olga Aleksandrova on 01-Jul-18.
 */
public class AlbumsListAdapter extends RecyclerView.Adapter<AlbumItemViewHolder> {

    private OnItemClickListener mListener;
    private List<Album> mResultItemsList = new ArrayList<>();

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public AlbumItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.li_result_item, parent, false);
        return new AlbumItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumItemViewHolder holder, int position) {
        holder.setOnItemClickListener(mListener);
        final Album album = mResultItemsList.get(position);
        holder.bindItem(album);
    }

    @Override
    public int getItemCount() {
        return mResultItemsList.size();
    }

    public void showResults(List<Album> items) {
        if (items != null && !items.isEmpty()) {
            mResultItemsList = items;
        }
        notifyDataSetChanged();
    }
}
