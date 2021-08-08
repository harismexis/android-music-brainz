package com.example.musicbrainz.tests.home

import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.example.musicbrainz.base.BaseUnitTest
import com.example.musicbrainz.framework.util.formatArtistsQuery
import com.example.musicbrainz.framework.util.resource.ResourceProvider
import com.example.musicbrainz.presentation.screens.home.viewmodel.HomeVm
import com.example.musicbrainz.presentation.screens.home.viewmodel.HomeVm.Companion.KEY_SEARCH_QUERY
import com.example.musicbrainz.usecases.UseCaseSearchArtists
import com.example.musicbrainz.util.result.ArtistsResult
import io.mockk.*
import io.mockk.impl.annotations.MockK

abstract class HomeVmBaseTest : BaseUnitTest() {

    @MockK
    protected lateinit var mockCaseSearchArtists: UseCaseSearchArtists
    @MockK
    protected lateinit var mockObserverArtists: Observer<ArtistsResult>
    @MockK
    protected lateinit var mockResourceProvider: ResourceProvider
    @MockK
    protected lateinit var mockStateHandle: SavedStateHandle
    private val artists = artistProvider.getMockArtistsFromFeedWithAllItemsValid()
    private val artistsSuccess = ArtistsResult.Success(artists)
    private val error = Exception(ERROR_MSG)
    private val artistsError = ArtistsResult.Error(error)
    private val searchInput = "Rory Gallagher"
    private val searchQuery = formatArtistsQuery(searchInput)

    protected lateinit var subject: HomeVm

    companion object {
        const val ERROR_MSG = "error"
        const val INTERNET_OFF_MSG = "please activate internet"
    }

    override fun onDoBefore() {
        initClassUnderTest()
        initLiveData()
    }

    private fun initClassUnderTest() {
        subject = HomeVm(
            mockCaseSearchArtists,
            mockResourceProvider,
            mockStateHandle
        )
        every { mockResourceProvider.getInternetOffMsg() } returns INTERNET_OFF_MSG
        every { mockObserverArtists.onChanged(any()) } just runs
        every { mockStateHandle.set(KEY_SEARCH_QUERY, searchQuery) } answers {  }
        every { mockStateHandle.get(KEY_SEARCH_QUERY) as? String} returns searchQuery
    }

    protected fun search() {
        subject.search(searchQuery)
    }

    protected fun mockSearchCall() {
        coEvery { mockCaseSearchArtists(searchQuery) } returns artists
    }

    protected fun mockSearchCallThrows() {
        coEvery { mockCaseSearchArtists(searchQuery) } throws error
    }

    protected fun verifySearchCallDone() {
        coVerify { mockCaseSearchArtists(searchQuery) }
    }

    protected fun initLiveData() {
        subject.artists.observeForever(mockObserverArtists)
    }

    protected fun verifySearchResultSuccess() {
        verify(exactly = 1) { mockObserverArtists.onChanged(artistsSuccess) }
    }

    protected fun verifySearchResultError() {
        verify(exactly = 1) { mockObserverArtists.onChanged(artistsError) }
    }
}