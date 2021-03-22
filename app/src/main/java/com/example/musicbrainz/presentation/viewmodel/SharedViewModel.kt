package com.example.musicbrainz.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.framework.extensions.getErrorMessage
import com.example.musicbrainz.framework.resource.ResourceProvider
import com.example.musicbrainz.framework.util.ConnectivityMonitor
import com.example.musicbrainz.framework.util.buildAlbumsQuery
import com.example.musicbrainz.framework.util.buildSearchQuery
import com.example.musicbrainz.interactors.InteractorGetAlbums
import com.example.musicbrainz.interactors.InteractorSearchArtists
import com.example.musicbrainz.presentation.result.AlbumsResult
import com.example.musicbrainz.presentation.result.ArtistsResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    private val irrSearchArtists: InteractorSearchArtists,
    private val irrGetAlbums: InteractorGetAlbums,
    private val connectivity: ConnectivityMonitor,
    private val resProvider: ResourceProvider
) : ViewModel() {

    private val TAG = SharedViewModel::class.qualifiedName

    private val mArtistsResult = MutableLiveData<ArtistsResult>()
    val artistsResult: LiveData<ArtistsResult>
        get() = mArtistsResult

    private val mAlbumsResult = MutableLiveData<AlbumsResult>()
    val albumsResult: LiveData<AlbumsResult>
        get() = mAlbumsResult

    lateinit var selectedArtist: Artist

    var searchQuery: String? = null
        set(value) {
            field = value
            value?.let {
                if (!value.isNullOrBlank()) {
                    onArtistSearched(value)
                }
            }
        }

    private fun onArtistSearched(name: String) {
        if (!connectivity.isOnline()) {
            mArtistsResult.value = ArtistsResult.ArtistsError(resProvider.getInternetOffMsg())
        } else {
            val query = buildSearchQuery(name)
            fetchArtists(query)
        }
    }

    private fun fetchArtists(name: String) {
        viewModelScope.launch {
            try {
                val items = irrSearchArtists.invoke(name)
                mArtistsResult.value = ArtistsResult.ArtistsSuccess(items)
            } catch (e: Exception) {
                Log.d(TAG, e.getErrorMessage())
                mArtistsResult.value = ArtistsResult.ArtistsError(e.getErrorMessage())
            }
        }
    }

    fun hasSelectedArtist(): Boolean {
        return this::selectedArtist.isInitialized
    }

    fun fetchAlbums() {
        if (!connectivity.isOnline()) {
            mAlbumsResult.value = AlbumsResult.AlbumsError(resProvider.getInternetOffMsg())
        } else {
            val query = buildAlbumsQuery(selectedArtist.id)
            fetchAlbums(query)
        }
    }

    private fun fetchAlbums(query: String) {
        viewModelScope.launch {
            try {
                val items = irrGetAlbums.invoke(query)
                mAlbumsResult.value = AlbumsResult.AlbumsSuccess(items)
            } catch (e: Exception) {
                Log.d(TAG, e.getErrorMessage())
                mAlbumsResult.value = AlbumsResult.AlbumsError(e.getErrorMessage())
            }
        }
    }
}