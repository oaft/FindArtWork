package com.example.inquallity.findartwork.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Olga Aleksandrova on 01-Jul-18.
 */
public class Album {

    @SerializedName("collectionName") private String mAlbumName;
    @SerializedName("artistName") private String mArtistName;
    @SerializedName("releaseDate") private String mReleaseDate; //2014-10-14T07:00:00Z
    @SerializedName("artworkUrl100") private String mImageUrl;
    @SerializedName("collectionId") private String mCollectionId;

    public String getAlbumName() {
        return mAlbumName;
    }

    public String getArtistName() {
        return mArtistName;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getCollectionId() {
        return mCollectionId;
    }
}
