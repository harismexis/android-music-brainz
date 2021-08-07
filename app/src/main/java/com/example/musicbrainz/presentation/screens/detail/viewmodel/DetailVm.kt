package com.example.musicbrainz.presentation.screens.detail.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.framework.util.buildAlbumsQuery
import com.example.musicbrainz.framework.util.extensions.getErrorMessage
import com.example.musicbrainz.framework.util.resource.ResourceProvider
import com.example.musicbrainz.usecases.UseCaseGetAlbums
import com.example.musicbrainz.util.event.Event
import com.example.musicbrainz.util.result.AlbumsResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailVm @Inject constructor(
    private val getAlbums: UseCaseGetAlbums,
    private val resProvider: ResourceProvider
) : ViewModel() {

    companion object {
        val TAG = DetailVm::class.qualifiedName
    }

    private val mAlbums = MutableLiveData<AlbumsResult>()
    val albums: LiveData<AlbumsResult>
        get() = mAlbums

    private val mShowMsg = MutableLiveData<Event<String>>()
    val showMsg: LiveData<Event<String>>
        get() = mShowMsg

    lateinit var selectedArtist: Artist

    fun hasSelectedArtist(): Boolean {
        return this::selectedArtist.isInitialized
    }

    fun fetchAlbums() {
        val query = buildAlbumsQuery(selectedArtist.id)
        fetchAlbums(query)
    }

    private fun fetchAlbums(query: String) {
        viewModelScope.launch {
            try {
                val items = getAlbums(query)
                mAlbums.value = AlbumsResult.Success(items)
            } catch (e: Exception) {
                Log.d(TAG, e.getErrorMessage())
                mAlbums.value = AlbumsResult.Error(e)
                mShowMsg.value = Event(e.getErrorMessage())
            }
        }
    }
}