package com.example.musicbrainz.presentation.screens.detail.viewmodel

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
import com.example.musicbrainz.interactors.InteractorGetAlbums
import com.example.musicbrainz.presentation.result.AlbumsResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val irrGetAlbums: InteractorGetAlbums,
    private val connectivity: ConnectivityMonitor,
    private val resProvider: ResourceProvider
) : ViewModel() {

    private val TAG = DetailViewModel::class.qualifiedName

    private val mAlbumsResult = MutableLiveData<AlbumsResult>()
    val albumsResult: LiveData<AlbumsResult>
        get() = mAlbumsResult

    lateinit var selectedArtist: Artist

    fun hasSelectedArtist(): Boolean {
        return this::selectedArtist.isInitialized
    }

    fun fetchAlbums() {
        if (!connectivity.isOnline()) {
            mAlbumsResult.value = AlbumsResult.Error(resProvider.getInternetOffMsg())
        } else {
            val query = buildAlbumsQuery(selectedArtist.id)
            fetchAlbums(query)
        }
    }

    private fun fetchAlbums(query: String) {
        viewModelScope.launch {
            try {
                val items = irrGetAlbums.invoke(query)
                mAlbumsResult.value = AlbumsResult.Success(items)
            } catch (e: Exception) {
                Log.d(TAG, e.getErrorMessage())
                mAlbumsResult.value = AlbumsResult.Error(e.getErrorMessage())
            }
        }
    }
}