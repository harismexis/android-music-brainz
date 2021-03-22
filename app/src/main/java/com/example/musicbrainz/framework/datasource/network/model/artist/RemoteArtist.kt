package com.example.musicbrainz.framework.datasource.network.model.artist

import com.google.gson.annotations.SerializedName

data class RemoteArtist(
    val id: String?,
    val type: String?,
    val score: Int?,
    val name: String?,
    val country: String?,
    val area: Area?,
    @SerializedName("begin-area")
    val beginArea: Area?,
    @SerializedName("end-area")
    val endArea: Area?,
    @SerializedName("life-span")
    val lifeSpan: LifeSpan?,
    val tags: List<Tag>?
)
