package com.example.inquallity.findartwork.api;

import com.example.inquallity.findartwork.model.Album;
import com.example.inquallity.findartwork.model.AlbumDetails;
import com.example.inquallity.findartwork.model.ResponsePage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Olga Aleksandrova on 01-Jul-18.
 */
public interface ItunesApi {

    @GET("/search?")
    Call<ResponsePage<Album>> getSearchData(@Query("term") String name, @Query("entity") String mediaType, @Query("attribute") String attribute);

    @GET("/lookup?")
    Call<ResponsePage<AlbumDetails>> getDetailsData(@Query("id") String idCollection, @Query("entity") String song);
}
