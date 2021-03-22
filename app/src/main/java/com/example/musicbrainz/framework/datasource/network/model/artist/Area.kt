package com.example.musicbrainz.framework.datasource.network.model.artist

import com.google.gson.annotations.SerializedName

data class Area(
    val type: String?,
    val name: String?,
    @SerializedName("sort-name")
    val sortName: String?,
)