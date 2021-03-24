package com.example.musicbrainz.home

import androidx.lifecycle.Observer
import com.example.musicbrainz.framework.resource.ResourceProvider
import com.example.musicbrainz.framework.util.ConnectivityMonitor
import com.example.musicbrainz.framework.util.buildSearchQuery
import com.example.musicbrainz.interactors.InteractorSearchArtists
import com.example.musicbrainz.parser.ArtistMockParser
import com.example.musicbrainz.presentation.result.ArtistsResult
import com.example.musicbrainz.presentation.screens.home.viewmodel.HomeViewModel
import com.example.musicbrainz.setup.UnitTestSetup
import io.mockk.*
import io.mockk.impl.annotations.MockK

abstract class HomeViewModelTestSetup : UnitTestSetup() {

    @MockK
    protected lateinit var mockIrrSearchArtists: InteractorSearchArtists
    @MockK
    protected lateinit var mockConnectivity: ConnectivityMonitor
    @MockK
    lateinit var mockObserverArtists: Observer<ArtistsResult>
    @MockK
    lateinit var mockResourceProvider: ResourceProvider

    private val artistParser = ArtistMockParser(fileParser)

    private val artists = artistParser.getMockArtistsFromFeedWithAllItemsValid()
    private val artistsSuccess = ArtistsResult.ArtistsSuccess(artists)
    private val artistsError = ArtistsResult.ArtistsError(ERROR_MSG)
    private val internetOffError = ArtistsResult.ArtistsError(INTERNET_OFF_MSG)

    private val searchArtistInput = "Rory Gallagher"
    private val searchQuery = buildSearchQuery(searchArtistInput)

    val mockSelectedArtist = artists[0]

    protected lateinit var subject: HomeViewModel

    companion object {
        const val ERROR_MSG = "error"
        const val INTERNET_OFF_MSG = "please activate internet"
    }

    override fun initialiseClassUnderTest() {
        subject = HomeViewModel(
            mockIrrSearchArtists,
            mockConnectivity,
            mockResourceProvider
        )
        every { mockResourceProvider.getInternetOffMsg() } returns INTERNET_OFF_MSG
        val selectedArtistId = mockSelectedArtist.id
        //albumsQuery = "arid:$selectedArtistId"
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