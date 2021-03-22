package com.example.musicbrainz.presentation.result

import com.example.musicbrainz.domain.Album

sealed class AlbumsResult {
    data class AlbumsSuccess(val items: List<Album>) : AlbumsResult()
    data class AlbumsError(val error: String) : AlbumsResult()
}