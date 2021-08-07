package com.example.musicbrainz.domain

import java.io.Serializable

data class Artist(
    val id: String,
    val type: String?,
    val score: Int?,
    val name: String?,
    val country: String?,
    val area: String?,
    val beginArea: String?,
    val endArea: String?,
    val beginDate: String?,
    val endDate: String?,
    val tags: List<String?>?
): Serializable