package com.example.musicbrainz.home

import androidx.lifecycle.Observer
import com.example.musicbrainz.framework.util.ConnectivityMonitor
import com.example.musicbrainz.framework.util.buildSearchQuery
import com.example.musicbrainz.framework.util.resource.ResourceProvider
import com.example.musicbrainz.usecases.InteractorSearchArtists
import com.example.musicbrainz.presentation.screens.home.viewmodel.HomeVm
import com.example.musicbrainz.reader.MockArtistProvider
import com.example.musicbrainz.setup.BaseUnitTest
import com.example.musicbrainz.util.result.ArtistsResult
import io.mockk.*
import io.mockk.impl.annotations.MockK

abstract class HomeVmBaseTest : BaseUnitTest() {

    @MockK
    protected lateinit var mockIrrSearchArtists: InteractorSearchArtists
    @MockK
    protected lateinit var mockConnectivity: ConnectivityMonitor
    @MockK
    protected lateinit var mockObserverArtists: Observer<ArtistsResult>
    @MockK
    protected lateinit var mockResourceProvider: ResourceProvider

    private val artistParser = MockArtistProvider(fileParser)
    private val artists = artistParser.getMockArtistsFromFeedWithAllItemsValid()
    private val artistsSuccess = ArtistsResult.Success(artists)
    private val artistsError = ArtistsResult.Error(ERROR_MSG)
    private val internetOffError = ArtistsResult.Error(INTERNET_OFF_MSG)
    private val searchArtistInput = "Rory Gallagher"
    private val searchQuery = buildSearchQuery(searchArtistInput)

    protected lateinit var subject: HomeVm

    companion object {
        const val ERROR_MSG = "error"
        const val INTERNET_OFF_MSG = "please activate internet"
    }

    override fun initialiseClassUnderTest() {
        subject = HomeVm(
            mockIrrSearchArtists,
            mockConnectivity,
            mockResourceProvider
        )
        every { mockResourceProvider.getInternetOffMsg() } returns INTERNET_OFF_MSG
        every { mockObserverArtists.onChanged(any()) } just runs
    }

    protected fun triggerSearchArtist() {
        subject.searchQuery = searchQuery
    }

    protected fun mockInternetActive(active: Boolean) {
        every { mockConnectivity.isOnline() } returns active
    }

    protected fun verifyInternetChecked() {
        verify(exactly = 1) { mockConnectivity.isOnline() }
    }

    protected fun mockSearchCall() {
        coEvery { mockIrrSearchArtists.invoke(searchQuery) } returns artists
    }

    protected fun mockSearchCallThrowsError() {
        coEvery { mockIrrSearchArtists.invoke(searchQuery) } throws IllegalStateException(ERROR_MSG)
    }

    protected fun verifySearchCallDone() {
        coVerify { mockIrrSearchArtists.invoke(searchQuery) }
    }

    protected fun verifySearchCallNotDone() {
        coVerify(exactly = 0) { mockIrrSearchArtists.invoke(any()) }
    }

    protected fun initialiseLiveData() {
        subject.artistsResult.observeForever(mockObserverArtists)
    }

    protected fun verifySearchResultSuccess() {
        verify(exactly = 1) { mockObserverArtists.onChanged(artistsSuccess) }
    }

    protected fun verifySearchResultError() {
        verify(exactly = 1) { mockObserverArtists.onChanged(artistsError) }
    }

    protected fun verifyResultInternetOff() {
        verify(exactly = 1) { mockObserverArtists.onChanged(internetOffError) }
    }

}