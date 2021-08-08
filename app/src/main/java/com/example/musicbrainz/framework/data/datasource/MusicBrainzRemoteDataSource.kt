package com.example.musicbrainz.framework.data.datasource

import com.example.musicbrainz.data.MusicBrainzBaseRemoteDataSource
import com.example.musicbrainz.domain.Album
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.framework.data.api.MusicBrainzApi
import com.example.musicbrainz.framework.util.extensions.toItems
import javax.inject.Inject

class MusicBrainzRemoteDataSource @Inject constructor(
    private val api: MusicBrainzApi
) : MusicBrainzBaseRemoteDataSource {

    override suspend fun getArtists(query: String): List<Artist> {
        return api.getArtists(query).toItems()
    }

    override suspend fun getAlbums(query: String): List<Album> {
        return api.getAlbums(query).toItems()
    }
}