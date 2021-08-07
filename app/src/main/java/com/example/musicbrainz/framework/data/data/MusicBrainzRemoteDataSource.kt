package com.example.musicbrainz.framework.data.data

import com.example.musicbrainz.data.MusicBrainzBaseRemoteDataSource
import com.example.musicbrainz.domain.Album
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.framework.util.extensions.toItems
import javax.inject.Inject

class MusicBrainzRemoteDataSource @Inject constructor(
    private val dao: MusicBrainzRemoteDao
) : MusicBrainzBaseRemoteDataSource {

    override suspend fun getArtists(query: String): List<Artist> {
        return dao.getArtists(query).toItems()
    }

    override suspend fun getAlbums(query: String): List<Album> {
        return dao.getAlbums(query).toItems()
    }
}