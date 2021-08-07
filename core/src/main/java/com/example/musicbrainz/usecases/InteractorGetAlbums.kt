package com.example.musicbrainz.usecases

import com.example.musicbrainz.data.MusicBrainzRemoteRepository

class InteractorGetAlbums(
    private val repository: MusicBrainzRemoteRepository
) {
    suspend operator fun invoke(query: String) = repository.getAlbums(query)
}