package com.example.inquallity.findartwork.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Olga Aleksandrova on 01-Jul-18.
 */
public class AlbumDetails {

    @SerializedName("collectionName") private String mAlbumName;
    @SerializedName("artistName") private String mArtistName;
    @SerializedName("releaseDate") private String mReleaseDate; //2014-10-14T07:00:00Z
    @SerializedName("primaryGenreName") private String mGenreName;
    @SerializedName("trackCount") private String mTrackCount;
    @SerializedName("copyright") private String mCopyright;
    @SerializedName("collectionPrice") private String mAlbumPrice;
    @SerializedName("artworkUrl100") private String mImageUrl;
    @SerializedName("collectionId") private String mCollectionId;
    @SerializedName("trackNumber") private String mTrackNumber;
    @SerializedName("trackName") private String mTrackName;
    @SerializedName("wrapperType") private String mWrapperType;

    public String getTrackNumber() {
        return mTrackNumber;
    }

    public String getTrackName() {
        return mTrackName;
    }

    public String getAlbumName() {
        return mAlbumName;
    }

    public String getArtistName() {
        return mArtistName;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getGenreName() {
        return mGenreName;
    }

    public String getTrackCount() {
        return mTrackCount;
    }

    public String getCopyright() {
        return mCopyright;
    }

    public String getAlbumPrice() {
        return mAlbumPrice;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getCollectionId() {
        return mCollectionId;
    }

    public String getWrapperType() {
        return mWrapperType;
    }
}
