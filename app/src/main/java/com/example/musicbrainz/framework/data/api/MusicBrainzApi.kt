package com.example.musicbrainz.framework.data.api

import com.example.musicbrainz.framework.data.model.album.AlbumsResponse
import com.example.musicbrainz.framework.data.model.artist.ArtistsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicBrainzApi {

    @GET("artist")
    suspend fun getArtists(
        @Query("query") query: String,
        @Query("fmt") format: String = "json"
    ): ArtistsResponse?

    @GET("release")
    suspend fun getAlbums(
        @Query("query") query: String,
        @Query("fmt") format: String = "json"
    ): AlbumsResponse?

}