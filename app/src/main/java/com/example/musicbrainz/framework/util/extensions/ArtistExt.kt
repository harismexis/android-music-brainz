package com.example.musicbrainz.framework.util.extensions

import com.example.musicbrainz.domain.Artist

fun Artist.tagString(): String? {
    return this.tags?.joinToString(", ")
}