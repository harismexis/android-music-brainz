package com.example.musicbrainz.home

import androidx.lifecycle.Observer
import com.example.musicbrainz.framework.util.ConnectivityMonitor
import com.example.musicbrainz.framework.util.buildSearchQuery
import com.example.musicbrainz.framework.util.resource.ResourceProvider
import com.example.musicbrainz.presentation.screens.home.viewmodel.HomeVm
import com.example.musicbrainz.setup.BaseUnitTest
import com.example.musicbrainz.usecases.UseCaseSearchArtists
import com.example.musicbrainz.util.result.ArtistsResult
import io.mockk.*
import io.mockk.impl.annotations.MockK

abstract class HomeVmBaseTest : BaseUnitTest() {

    @MockK
    protected lateinit var mockIrrSearchArtists: UseCaseSearchArtists
    @MockK
    protected lateinit var mockConnectivity: ConnectivityMonitor
    @MockK
    protected lateinit var mockObserverArtists: Observer<ArtistsResult>
    @MockK
    protected lateinit var mockResourceProvider: ResourceProvider

    private val artists = artistProvider.getMockArtistsFromFeedWithAllItemsValid()
    private val artistsSuccess = ArtistsResult.Success(artists)
    val error = Exception(ERROR_MSG)
    private val artistsError = ArtistsResult.Error(error)
    private val searchArtistInput = "Rory Gallagher"
    private val searchQuery = buildSearchQuery(searchArtistInput)

    protected lateinit var subject: HomeVm

    companion object {
        const val ERROR_MSG = "error"
        const val INTERNET_OFF_MSG = "please activate internet"
    }

    override fun onDoBefore() {
        initClassUnderTest()
        initialiseLiveData()
    }

    private fun initClassUnderTest() {
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

    protected fun mockSearchCall() {
        coEvery { mockIrrSearchArtists(searchQuery) } returns artists
    }

    protected fun mockSearchCallThrows() {
        coEvery { mockIrrSearchArtists(searchQuery) } throws error
    }

    protected fun verifySearchCallDone() {
        coVerify { mockIrrSearchArtists(searchQuery) }
    }

    protected fun verifySearchCallNotDone() {
        coVerify(exactly = 0) { mockIrrSearchArtists(any()) }
    }

    protected fun initialiseLiveData() {
        subject.artists.observeForever(mockObserverArtists)
    }

    protected fun verifySearchResultSuccess() {
        verify(exactly = 1) { mockObserverArtists.onChanged(artistsSuccess) }
    }

    protected fun verifySearchResultError() {
        verify(exactly = 1) { mockObserverArtists.onChanged(artistsError) }
    }
}