package com.example.musicbrainz.framework.extensions

import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.framework.parcelable.ArtistParcel

fun Artist.tagString(): String? {
    return this.tags?.joinToString(", ")
}

fun Artist.toParcel(): ArtistParcel {
    return ArtistParcel(
        this.id,
        this.type,
        this.score,
        this.name,
        this.country,
        this.area,
        this.beginArea,
        this.endArea,
        this.beginDate,
        this.endDate,
        this.tags
    )
}

fun ArtistParcel.toArtist(): Artist {
    return Artist(
        this.id,
        this.type,
        this.score,
        this.name,
        this.country,
        this.area,
        this.beginArea,
        this.endArea,
        this.beginDate,
        this.endDate,
        this.tags
    )
}