package com.example.musicbrainz.setup.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.musicbrainz.presentation.result.AlbumsResult
import com.example.musicbrainz.presentation.screens.detail.viewmodel.DetailViewModel
import io.mockk.mockk

object MockDetailVmProvider {

    val mockDetailViewModel: DetailViewModel = mockk(relaxed = true)
    var albumsResult = MutableLiveData<AlbumsResult>()

}