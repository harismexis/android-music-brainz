package com.example.musicbrainz.tests

import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import com.example.musicbrainz.R
import com.example.musicbrainz.base.BaseInstrumentedTest
import com.example.musicbrainz.config.vmfactory.mockHomeVm
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.presentation.screens.activity.MainActivity
import com.example.musicbrainz.util.event.Event
import com.example.musicbrainz.util.result.ArtistsResult
import com.example.musicbrainz.util.searchview.submitSearchQuery
import io.mockk.every

open class BaseSearchFlowTest: BaseInstrumentedTest() {

    protected val searchResult = MutableLiveData<ArtistsResult>()
    protected val showMsg = MutableLiveData<Event<String>>()
    protected lateinit var mockArtists: List<Artist>

    protected fun startActivity(): ActivityScenario<MainActivity> {
        return launchActivity()
    }

    protected fun mockSearchResults(
        mockData: List<Artist>
    ) {
        mockArtists = mockData
        every { mockHomeVm.search(any()) } answers {
            searchResult.value = ArtistsResult.Success(mockArtists)
        }
        every { mockHomeVm.artists } returns searchResult
    }

    protected fun performSearch() {
        submitSearchQuery(R.id.searchView, "Metallica")
    }

}