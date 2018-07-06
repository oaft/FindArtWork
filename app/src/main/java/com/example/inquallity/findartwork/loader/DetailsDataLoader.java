package com.example.inquallity.findartwork.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.inquallity.findartwork.api.ItunesApi;
import com.example.inquallity.findartwork.model.AlbumDetails;
import com.example.inquallity.findartwork.model.ResponsePage;
import com.example.inquallity.findartwork.model.Result;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * This class executes search in iTunes via public API with supplied collection`s id
 *
 * @author Olga Aleksandrova on 03-Jul-18.
 */
public class DetailsDataLoader extends AsyncTaskLoader<Result> {

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
    public Result loadInBackground() {
        final Call<ResponsePage<AlbumDetails>> detailsData = mItunesApi.getDetailsData(mIdCollection, MEDIA_TYPE);
        ResponsePage<AlbumDetails> responsePage;
        try {
            final Response<ResponsePage<AlbumDetails>> response = detailsData.execute();
            responsePage = response.body();
            final List<AlbumDetails> albumDetails = responsePage.getItems();
            if (responsePage != null) {
                return new Result(albumDetails);
            }
        } catch (IOException e) {
            Log.e(DetailsDataLoader.class.getSimpleName(), e.getMessage(), e);
            return new Result(e.getMessage());
        }
        return null;
    }

    @Override
    public void deliverResult(Result data) {
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
