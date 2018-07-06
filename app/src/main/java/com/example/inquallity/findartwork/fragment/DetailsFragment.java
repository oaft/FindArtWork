package com.example.inquallity.findartwork.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.inquallity.findartwork.AppDelegate;
import com.example.inquallity.findartwork.DateTimeUtil;
import com.example.inquallity.findartwork.R;
import com.example.inquallity.findartwork.api.ItunesApi;
import com.example.inquallity.findartwork.loader.DetailsDataLoader;
import com.example.inquallity.findartwork.model.AlbumDetails;
import com.example.inquallity.findartwork.model.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Olga Aleksandrova on 01-Jul-18.
 */
public class DetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Result> {

    @BindView(R.id.iv_album_details) ImageView mTvImageAlbum;
    @BindView(R.id.tv_album_name) TextView mTvAlbumName;
    @BindView(R.id.tv_release_date) TextView mTvReleaseDate;
    @BindView(R.id.tv_genre_name) TextView mTvGenreName;
    @BindView(R.id.tv_track_count) TextView mTvTrackCount;
    @BindView(R.id.tv_artist_name) TextView mTvArtistName;
    @BindView(R.id.tv_copyright) TextView mTvCopyright;
    @BindView(R.id.tv_album_price) TextView mTvAlbumPrice;
    @BindView(R.id.pb_load_indicator_details) ProgressBar mProgressBar;
    @BindView(R.id.tv_error_msg_details) TextView mTvErrorMsg;
    @BindView(R.id.cv_info) CardView mCvInfo;
    @BindView(R.id.cv_tracks) CardView mCvTracks;
    @BindView(R.id.ll_tracks_list) ViewGroup mTracksContainer;

    private Unbinder mUnbinder;
    private String mIdCollection;
    private String mAlbumName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fmt_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        mUnbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            mIdCollection = getArguments().getString("KEY_ID");
            mAlbumName = getArguments().getString("KEY_NAME");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mProgressBar.setVisibility(View.VISIBLE);
        getLoaderManager().initLoader(R.id.details_data_loader, null, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            getActivity().setTitle(mAlbumName);
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @NonNull
    @Override
    public Loader<Result> onCreateLoader(int id, @Nullable Bundle args) {
        return new DetailsDataLoader(getActivity(), AppDelegate.sRetrofit.create(ItunesApi.class), mIdCollection);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Result> loader, Result result) {

        mProgressBar.setVisibility(View.GONE);
        if (result != null) {
            if (result.isSuccess() && !result.getData().isEmpty()) {
                showData(result.getData());
                mCvInfo.setVisibility(View.VISIBLE);
                mCvTracks.setVisibility(View.VISIBLE);
            } else if (result.isSuccess()) {
                mTvErrorMsg.setText(R.string.nth_found_msg);
                mTvErrorMsg.setVisibility(View.VISIBLE);
            } else {
                mTvErrorMsg.setText(getString(R.string.error_msg, result.getErrorMessage()));
                mTvErrorMsg.setVisibility(View.VISIBLE);
            }
        } else {
            mTvErrorMsg.setText(R.string.nth_found_msg);
            mTvErrorMsg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Result> loader) {

    }

    private void showData(List<AlbumDetails> items) {
        mTracksContainer.removeAllViews();
        AlbumDetails albumDetails = new AlbumDetails();

        int count = 0;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getWrapperType().equals("collection") && count == 0) {
                albumDetails = items.get(i);
                items.remove(i);
                count++;
            }
            final String trackNumber = items.get(i).getTrackNumber();
            final String trackName = items.get(i).getTrackName();
            final String track = getString(R.string.track_pattern, trackNumber, trackName);
            if (items.get(i).getWrapperType().equals("track")) {
                final TextView tvTrackName = new TextView(getActivity());
                tvTrackName.setText(track);
                mTracksContainer.addView(tvTrackName);
            }
        }

        Picasso.get().load(albumDetails.getImageUrl()).into(mTvImageAlbum);
        mTvAlbumName.setText(albumDetails.getAlbumName());
        final long releaseDateMillis = DateTimeUtil.parse(albumDetails.getReleaseDate(), "yyyy-MM-dd'T'HH:mm:ss'Z'");
        final String releaseDateStr = DateTimeUtil.format(releaseDateMillis, "dd.MM.yyyy");
        mTvReleaseDate.setText(releaseDateStr);
        mTvGenreName.setText(albumDetails.getGenreName());
        mTvTrackCount.setText(albumDetails.getTrackCount());
        mTvArtistName.setText(albumDetails.getArtistName());
        mTvCopyright.setText(albumDetails.getCopyright());
        mTvAlbumPrice.setText(getString(R.string.price_pattern, albumDetails.getAlbumPrice()));
    }
}
