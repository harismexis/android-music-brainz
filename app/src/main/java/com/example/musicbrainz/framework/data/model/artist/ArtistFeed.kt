package com.example.musicbrainz.framework.data.model.artist

import com.example.musicbrainz.framework.datasource.network.model.artist.RemoteArtist

data class ArtistFeed(
    val artists: List<RemoteArtist?>?
)