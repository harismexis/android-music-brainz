package com.example.musicbrainz.data

import com.example.musicbrainz.domain.Album
import com.example.musicbrainz.domain.Artist

data class MusicBrainzRemoteRepository(
    private val dataSource: MusicBrainzBaseRemoteDataSource
) {
    suspend fun getArtists(query: String): List<Artist> = dataSource.getArtists(query)

    suspend fun getAlbums(query: String): List<Album> = dataSource.getAlbums(query)
}