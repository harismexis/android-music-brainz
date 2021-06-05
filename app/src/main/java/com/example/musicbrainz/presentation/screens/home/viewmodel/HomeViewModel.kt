package com.example.musicbrainz.presentation.screens.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicbrainz.framework.event.Event
import com.example.musicbrainz.framework.extensions.getErrorMessage
import com.example.musicbrainz.framework.resource.ResourceProvider
import com.example.musicbrainz.framework.util.ConnectivityMonitor
import com.example.musicbrainz.framework.util.buildSearchQuery
import com.example.musicbrainz.interactors.InteractorSearchArtists
import com.example.musicbrainz.presentation.result.ArtistsResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val irrSearchArtists: InteractorSearchArtists,
    private val connectivity: ConnectivityMonitor,
    private val resProvider: ResourceProvider
) : ViewModel() {

    private val TAG = HomeViewModel::class.qualifiedName

    private val mArtistsResult = MutableLiveData<ArtistsResult>()
    val artistsResult: LiveData<ArtistsResult>
        get() = mArtistsResult

    private val mShowErrorMessage = MutableLiveData<Event<String>>()
    val showErrorMessage : LiveData<Event<String>>
        get() = mShowErrorMessage

    var searchQuery: String? = null
        set(value) {
            field = value
            value?.let {
                if (!value.isNullOrBlank()) {
                    onSearchQueryUpdated(value)
                }
            }
        }

    private fun onSearchQueryUpdated(name: String) {
        if (!connectivity.isOnline()) {
            mArtistsResult.value = ArtistsResult.Error(resProvider.getInternetOffMsg())
            mShowErrorMessage.value = Event(resProvider.getInternetOffMsg())
        }
        else fetchArtists(buildSearchQuery(name))
    }

    private fun fetchArtists(query: String) {
        viewModelScope.launch {
            try {
                val items = irrSearchArtists.invoke(query)
                mArtistsResult.value = ArtistsResult.Success(items)
            } catch (e: Exception) {
                Log.d(TAG, e.getErrorMessage())
                mArtistsResult.value = ArtistsResult.Error(e.getErrorMessage())
                mShowErrorMessage.value = Event(e.getErrorMessage())
            }
        }
    }

}