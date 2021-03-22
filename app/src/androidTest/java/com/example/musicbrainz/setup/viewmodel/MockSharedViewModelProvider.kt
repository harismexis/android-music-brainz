package com.example.musicbrainz.setup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicbrainz.presentation.result.AlbumsResult
import com.example.musicbrainz.presentation.result.ArtistsResult
import com.example.musicbrainz.presentation.viewmodel.SharedViewModel
import io.mockk.mockk

object MockSharedViewModelProvider {

    val mockSharedViewModel: SharedViewModel = mockk(relaxed = true)

    var mArtistsResult = MutableLiveData<ArtistsResult>()
    val artistsResult: LiveData<ArtistsResult>
        get() = mArtistsResult

    var mAlbumsResult = MutableLiveData<AlbumsResult>()
    val albumsResult: LiveData<AlbumsResult>
        get() = mAlbumsResult

}