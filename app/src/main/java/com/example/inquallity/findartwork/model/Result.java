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
public class Result<T> {

    private boolean mIsSuccess;
    private List<T> mData = new ArrayList<>();
    private String mErrorMessage;

    /**
     * Constructor for success result
     *
     * @param data Request result collection
     */
    public Result(List<T> data) {
        mIsSuccess = true;
        mData = data;
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
    public List<T> getData() {
        return mData;
    }

    @Nullable
    public String getErrorMessage() {
        return mErrorMessage;
    }
}
