package com.example.musicbrainz.framework.data.model.album

import com.google.gson.annotations.SerializedName

data class RemoteAlbum(
    val id: String?,
    val score: Int?,
    val title: String?,
    val status: String?,
    val disambiguation: String?,
    val packaging: String?,
    val date: String?,
    val country: String?,
    val barcode: String?,
    @SerializedName("track-count")
    val trackCount: Int?
)
