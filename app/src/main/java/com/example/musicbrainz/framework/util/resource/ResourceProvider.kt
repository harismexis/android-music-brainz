package com.example.musicbrainz.framework.util.resource

import android.content.Context
import androidx.annotation.StringRes
import com.example.musicbrainz.R
import javax.inject.Singleton

@Singleton
class ResourceProvider(context: Context) {

    private val appContext = context.applicationContext

    fun getString(@StringRes id: Int): String {
        return appContext.getString(id)
    }

    fun getInternetOffMsg(): String {
        return getString(R.string.please_activate_internet)
    }
}