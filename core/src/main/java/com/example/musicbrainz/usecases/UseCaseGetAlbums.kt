package com.example.musicbrainz.usecases

import com.example.musicbrainz.data.repository.MusicBrainzRemoteRepository

class UseCaseGetAlbums(
    private val repository: MusicBrainzRemoteRepository
) {
    suspend operator fun invoke(query: String) = repository.getAlbums(query)
}
