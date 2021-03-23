package com.example.musicbrainz.domain

data class Album(
    val id: String,
    val score: Int?,
    val title: String?,
    val status: String?,
    val disambiguation: String?,
    val packaging: String?,
    val date: String?,
    val country: String?,
    val barcode: String?,
    val trackCount: Int?
)

