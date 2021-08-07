package com.example.musicbrainz.framework.util.parcelable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtistParcel(
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
): Parcelable