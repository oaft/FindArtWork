package com.example.inquallity.findartwork.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Olga Aleksandrova on 01-Jul-18.
 */
public class ResponsePage<T> {

    @SerializedName("results") private List<T> mItems;

    public List<T> getItems() {
        return mItems;
    }
}
