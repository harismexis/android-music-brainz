package com.example.musicbrainz.presentation.screens.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicbrainz.framework.util.buildSearchQuery
import com.example.musicbrainz.framework.util.extensions.getErrorMessage
import com.example.musicbrainz.framework.util.resource.ResourceProvider
import com.example.musicbrainz.usecases.UseCaseSearchArtists
import com.example.musicbrainz.util.event.Event
import com.example.musicbrainz.util.result.ArtistsResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeVm @Inject constructor(
    private val searchArtists: UseCaseSearchArtists,
    private val resProvider: ResourceProvider
) : ViewModel() {

    companion object {
        val TAG = HomeVm::class.qualifiedName
    }

    private val mArtists = MutableLiveData<ArtistsResult>()
    val artists: LiveData<ArtistsResult>
        get() = mArtists

    private val mShowMsg = MutableLiveData<Event<String>>()
    val showMsg: LiveData<Event<String>>
        get() = mShowMsg

    var searchQuery: String? = null
        set(value) {
            field = value
            value?.let {
                if (!value.isNullOrBlank()) {
                    fetchArtists(buildSearchQuery(value))
                }
            }
        }

    private fun fetchArtists(query: String) {
        viewModelScope.launch {
            try {
                val items = searchArtists(query)
                mArtists.value = ArtistsResult.Success(items)
            } catch (e: Exception) {
                Log.d(TAG, e.getErrorMessage())
                mArtists.value = ArtistsResult.Error(e)
                mShowMsg.value = Event(e.getErrorMessage())
            }
        }
    }

}