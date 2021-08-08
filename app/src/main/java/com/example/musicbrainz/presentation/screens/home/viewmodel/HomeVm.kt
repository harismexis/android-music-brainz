package com.example.musicbrainz.presentation.screens.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.musicbrainz.framework.util.extensions.getErrorMessage
import com.example.musicbrainz.framework.util.resource.ResourceProvider
import com.example.musicbrainz.presentation.base.BaseVm
import com.example.musicbrainz.usecases.UseCaseSearchArtists
import com.example.musicbrainz.util.result.ArtistsResult
import kotlinx.coroutines.launch

class HomeVm (
    private val searchArtists: UseCaseSearchArtists,
    private val resProvider: ResourceProvider,
    private val stateHandle: SavedStateHandle
) : BaseVm() {

    companion object {
        val TAG = HomeVm::class.qualifiedName
        const val KEY_SEARCH_QUERY = "search_query"
    }

    private val mArtists = MutableLiveData<ArtistsResult>()
    val artists: LiveData<ArtistsResult>
        get() = mArtists

    fun search(query: String) {
        stateHandle[KEY_SEARCH_QUERY] = query
        executeSearch(query)
    }

    fun getSearchQuery(): String? {
        return stateHandle[KEY_SEARCH_QUERY]
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