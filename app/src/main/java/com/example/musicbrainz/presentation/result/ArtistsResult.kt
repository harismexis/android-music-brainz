package com.example.musicbrainz.presentation.result

import com.example.musicbrainz.domain.Artist

sealed class ArtistsResult {
    data class ArtistsSuccess(val items: List<Artist>): ArtistsResult()
    data class ArtistsError(val error: String): ArtistsResult()
}