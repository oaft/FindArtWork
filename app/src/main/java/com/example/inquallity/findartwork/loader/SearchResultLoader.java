package com.example.inquallity.findartwork.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.inquallity.findartwork.api.ItunesApi;
import com.example.inquallity.findartwork.model.Album;
import com.example.inquallity.findartwork.model.ResponsePage;
import com.example.inquallity.findartwork.model.Result;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * This class executes search in iTunes via public API with supplied query
 *
 * @author Olga Aleksandrova on 03-Jul-18.
 */
public class SearchResultLoader extends AsyncTaskLoader<Result> {

    private final ItunesApi mItunesApi;
    private String mQuery;
    private Result mRespPageCache = null;
    private static final String MEDIA_TYPE = "album";
    private static final String ATTRIBUTE = "albumTerm";

    /**
     * Constructor for SearchResultLoader class
     *
     * @param context Activity`s context
     * @param api     Public API for requests
     * @param query   Supplied query
     */
    public SearchResultLoader(Context context, ItunesApi api, String query) {
        super(context);
        mItunesApi = api;
        mQuery = query;
    }

    @Override
    public Result loadInBackground() {
        final Call<ResponsePage<Album>> searchData = mItunesApi.getSearchData(mQuery, MEDIA_TYPE, ATTRIBUTE);
        ResponsePage<Album> responsePage;
        try {
            final Response<ResponsePage<Album>> response = searchData.execute();
            responsePage = response.body();
            final List<Album> albums;
            if (responsePage != null) {
                albums = responsePage.getItems();
                Collections.sort(albums, new Comparator<Album>() {
                    @Override
                    public int compare(Album o1, Album o2) {
                        return o1.getAlbumName().compareTo(o2.getAlbumName());
                    }
                });
                return new Result(albums);
            }
        } catch (IOException e) {
            Log.e(SearchResultLoader.class.getSimpleName(), e.getMessage(), e);
            return new Result(e.getMessage());
        }
        return null;
    }

    @Override
    public void deliverResult(Result data) {
        if (mRespPageCache == null) {
            mRespPageCache = data;
        }
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if (mRespPageCache != null) {
            deliverResult(mRespPageCache);
        } else {
            forceLoad();
        }
    }
}
