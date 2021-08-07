package com.example.musicbrainz.result

import com.example.musicbrainz.domain.Artist

sealed class ArtistsResult {
    data class Success(val items: List<Artist>): ArtistsResult()
    data class Error(val error: String): ArtistsResult()
}