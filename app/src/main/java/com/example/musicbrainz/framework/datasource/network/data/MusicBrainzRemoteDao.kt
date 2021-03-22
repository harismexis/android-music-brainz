package com.example.musicbrainz.framework.datasource.network.data

import com.example.musicbrainz.framework.datasource.network.api.MusicBrainzApi
import com.example.musicbrainz.framework.datasource.network.model.album.AlbumFeed
import com.example.musicbrainz.framework.datasource.network.model.artist.ArtistFeed
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicBrainzRemoteDao @Inject constructor(private val api: MusicBrainzApi) {

    suspend fun getArtists(query: String): ArtistFeed? {
        return api.getArtists(query)
    }

    suspend fun getAlbums(query: String): AlbumFeed? {
        return api.getAlbums(query)
    }

}