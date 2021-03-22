package com.example.musicbrainz.interactors

import com.example.musicbrainz.data.MusicBrainzRemoteRepository

class InteractorSearchArtists(
    private val repository: MusicBrainzRemoteRepository
) {
    suspend operator fun invoke(query: String) = repository.getArtists(query)
}
