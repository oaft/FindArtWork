package com.example.inquallity.findartwork.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.inquallity.findartwork.api.ItunesApi;
import com.example.inquallity.findartwork.model.AlbumDetails;
import com.example.inquallity.findartwork.model.ResponsePage;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * This class executes search in iTunes via public API with supplied collection`s id
 *
 * @author Olga Aleksandrova on 03-Jul-18.
 */
public class DetailsDataLoader extends AsyncTaskLoader<ResponsePage<AlbumDetails>> {

    private final ItunesApi mItunesApi;
    private String mIdCollection;
    private static final String MEDIA_TYPE = "song";

    /**
     * Constructor for DetailsDataLoader class
     *
     * @param context      Activity`s context
     * @param itunesApi    Public API for requests
     * @param idCollection Supplied collection`s id
     */
    public DetailsDataLoader(@NonNull Context context, ItunesApi itunesApi, String idCollection) {
        super(context);
        mItunesApi = itunesApi;
        mIdCollection = idCollection;
    }

    @Nullable
    @Override
    public ResponsePage<AlbumDetails> loadInBackground() {
        final Call<ResponsePage<AlbumDetails>> detailsData = mItunesApi.getDetailsData(mIdCollection, MEDIA_TYPE);
        ResponsePage<AlbumDetails> responsePage = null;
        try {
            final Response<ResponsePage<AlbumDetails>> response = detailsData.execute();
            responsePage = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responsePage;
    }

    @Override
    public void deliverResult(ResponsePage<AlbumDetails> data) {
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
