package com.example.musicbrainz.presentation.screens.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicbrainz.framework.util.extensions.getErrorMessage
import com.example.musicbrainz.framework.util.resource.ResourceProvider
import com.example.musicbrainz.presentation.base.BaseVm
import com.example.musicbrainz.usecases.UseCaseSearchArtists
import com.example.musicbrainz.util.result.ArtistsResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeVm @Inject constructor(
    private val searchArtists: UseCaseSearchArtists,
    private val resProvider: ResourceProvider
) : BaseVm() {

    companion object {
        val TAG = HomeVm::class.qualifiedName
    }

    private val mArtists = MutableLiveData<ArtistsResult>()
    val artists: LiveData<ArtistsResult>
        get() = mArtists

    private var searchQuery: String? = null

    fun search(query: String) {
        searchQuery = query
        searchQuery?.let {
            executeSearch(it)
        }
    }

    private fun executeSearch(query: String) {
        viewModelScope.launch {
            try {
                val items = searchArtists(query)
                mArtists.value = ArtistsResult.Success(items)
            } catch (e: Exception) {
                Log.d(TAG, e.getErrorMessage())
                mArtists.value = ArtistsResult.Error(e)
                emitMsgEvent(e.getErrorMessage())
            }
        }
    }

}