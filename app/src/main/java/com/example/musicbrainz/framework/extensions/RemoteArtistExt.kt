package com.example.musicbrainz.framework.extensions

import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.framework.datasource.network.model.artist.ArtistFeed
import com.example.musicbrainz.framework.datasource.network.model.artist.RemoteArtist

fun ArtistFeed?.toItems(): List<Artist> {
    val items = mutableListOf<Artist>()
    if (this == null || this.artists == null) return items.toList()
    val filteredList = this.artists.filter { it != null && !it.id.isNullOrBlank() }
    items.addAll(filteredList.map {
        it!!.toItem(it.id!!)
    })
    return items.toList()
}

private fun RemoteArtist.toItem(id: String): Artist {
    return Artist(
        id,
        this.type,
        this.score,
        this.name,
        this.country,
        this.area?.name,
        this.beginArea?.name,
        this.endArea?.name,
        this.lifeSpan?.begin,
        this.lifeSpan?.end,
        this.tags?.map { it.name }
    )
}
