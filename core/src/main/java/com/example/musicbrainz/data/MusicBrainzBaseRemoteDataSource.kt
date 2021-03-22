package com.example.musicbrainz.data

import com.example.musicbrainz.domain.Album
import com.example.musicbrainz.domain.Artist

interface MusicBrainzBaseRemoteDataSource {

    suspend fun getArtists(query: String): List<Artist>

    suspend fun getAlbums(query: String): List<Album>

}