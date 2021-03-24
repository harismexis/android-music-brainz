package com.example.musicbrainz.setup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicbrainz.presentation.result.ArtistsResult
import com.example.musicbrainz.presentation.screens.home.viewmodel.HomeViewModel
import io.mockk.mockk

object MockHomeViewModelProvider {

    val mockHomeViewModel: HomeViewModel = mockk(relaxed = true)

    var mArtistsResult = MutableLiveData<ArtistsResult>()
    val artistsResult: LiveData<ArtistsResult>
        get() = mArtistsResult

}