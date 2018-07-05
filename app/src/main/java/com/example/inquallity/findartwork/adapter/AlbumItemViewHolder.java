package com.example.inquallity.findartwork.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inquallity.findartwork.DateTimeUtil;
import com.example.inquallity.findartwork.R;
import com.example.inquallity.findartwork.model.Album;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Olga Aleksandrova on 01-Jul-18.
 */
public class AlbumItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.iv_album_result) ImageView mImage;
    @BindView(R.id.tv_item_album_name) TextView mAlbumName;
    @BindView(R.id.tv_item_artist_name) TextView mArtistName;
    @BindView(R.id.tv_item_release_date) TextView mReleaseDate;
    private OnItemClickListener mListener;
    private Album mAlbum;

    public AlbumItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void bindItem(Album album) {
        mAlbum = album;
        mAlbumName.setText(album.getAlbumName());
        mArtistName.setText(album.getArtistName());
        final long releaseDateMillis = DateTimeUtil.parse(album.getReleaseDate(), "yyyy-MM-dd'T'HH:mm:ss'Z'");
        final String releaseDateStr = DateTimeUtil.format(releaseDateMillis, "yyyy");
        mReleaseDate.setText(releaseDateStr);
        Picasso.get().load(album.getImageUrl()).into(mImage);
    }

    @Override
    public void onClick(View v) {
        mListener.onItemClick(v, mAlbum.getCollectionId(), mAlbum.getAlbumName());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
