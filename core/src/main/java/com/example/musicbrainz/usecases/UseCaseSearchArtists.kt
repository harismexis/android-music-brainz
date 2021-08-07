package com.example.musicbrainz.usecases

import com.example.musicbrainz.data.MusicBrainzRemoteRepository

class UseCaseSearchArtists(
    private val repository: MusicBrainzRemoteRepository
) {
    suspend operator fun invoke(query: String) = repository.getArtists(query)
}
