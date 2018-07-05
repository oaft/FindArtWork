package com.example.inquallity.findartwork.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class wraps result of loader`s work.
 *
 * @author Olga Aleksandrova on 04-Jul-18.
 */
public class Result {

    private boolean mIsSuccess;
    private List<Album> mAlbums = new ArrayList<>();
    private String mErrorMessage;

    /**
     * Constructor for success result
     *
     * @param albums Request result collection
     */
    public Result(List<Album> albums) {
        mIsSuccess = true;
        mAlbums = albums;
    }

    /**
     * Constructor for failed request
     *
     * @param errorMessage Some message about error
     */
    public Result(String errorMessage) {
        mIsSuccess = false;
        mErrorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return mIsSuccess;
    }

    @NonNull
    public List<Album> getAlbums() {
        return mAlbums;
    }

    @Nullable
    public String getErrorMessage() {
        return mErrorMessage;
    }
}
