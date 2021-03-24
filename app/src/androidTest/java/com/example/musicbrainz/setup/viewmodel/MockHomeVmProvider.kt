package com.example.musicbrainz.setup.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.musicbrainz.presentation.result.ArtistsResult
import com.example.musicbrainz.presentation.screens.home.viewmodel.HomeViewModel
import io.mockk.mockk

object MockHomeVmProvider {

    val mockHomeViewModel: HomeViewModel = mockk(relaxed = true)
    var artistsResult = MutableLiveData<ArtistsResult>()

}