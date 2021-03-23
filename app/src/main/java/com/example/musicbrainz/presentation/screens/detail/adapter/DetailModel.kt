package com.example.musicbrainz.presentation.screens.detail.adapter

import com.example.musicbrainz.domain.Album
import com.example.musicbrainz.domain.Artist

sealed class DetailModel {
    data class ArtistHeaderModel(val artist: Artist) : DetailModel()
    data class TextModel(val title: String) : DetailModel()
    data class AlbumModel(val album: Album) : DetailModel()
}