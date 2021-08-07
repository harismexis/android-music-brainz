package com.example.musicbrainz.framework.util.extensions

import com.example.musicbrainz.domain.Album
import com.example.musicbrainz.framework.data.model.album.AlbumsResponse
import com.example.musicbrainz.framework.data.model.album.RemoteAlbum

fun AlbumsResponse?.toItems(): List<Album> {
    val items = mutableListOf<Album>()
    if (this == null || this.releases == null) return items.toList()
    val filteredList = this.releases.filter { it.isValid() }
    items.addAll(filteredList.map {
        it!!.toItem(it.id!!)
    })
    return items.toList()
}

private fun RemoteAlbum.toItem(id: String): Album {
    return Album(
        id,
        this.score,
        this.title,
        this.status,
        this.disambiguation,
        this.packaging,
        this.date,
        this.country,
        this.barcode,
        this.trackCount
    )
}

private fun RemoteAlbum?.isValid(): Boolean {
    this?.let {
        return !it.id.isNullOrBlank() && !it.title.isNullOrBlank()
    }
    return false
}