package com.example.musicbrainz.framework.data.api

import com.example.musicbrainz.framework.data.model.album.AlbumFeed
import com.example.musicbrainz.framework.data.model.artist.ArtistFeed
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicBrainzApi {

    @GET("artist")
    suspend fun getArtists(
        @Query("query") query: String,
        @Query("fmt") format: String = "json"
    ): ArtistFeed?

    @GET("release")
    suspend fun getAlbums(
        @Query("query") query: String,
        @Query("fmt") format: String = "json"
    ): AlbumFeed?

}