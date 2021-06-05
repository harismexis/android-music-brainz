package com.example.musicbrainz.presentation.result

import com.example.musicbrainz.domain.Album

sealed class AlbumsResult {
    data class Success(val items: List<Album>) : AlbumsResult()
    data class Error(val error: String) : AlbumsResult()
}