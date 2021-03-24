package com.example.musicbrainz.detail

import androidx.lifecycle.Observer
import com.example.musicbrainz.framework.resource.ResourceProvider
import com.example.musicbrainz.framework.util.ConnectivityMonitor
import com.example.musicbrainz.framework.util.buildAlbumsQuery
import com.example.musicbrainz.interactors.InteractorGetAlbums
import com.example.musicbrainz.parser.AlbumMockParser
import com.example.musicbrainz.parser.ArtistMockParser
import com.example.musicbrainz.presentation.result.AlbumsResult
import com.example.musicbrainz.presentation.result.ArtistsResult
import com.example.musicbrainz.presentation.screens.detail.viewmodel.DetailViewModel
import com.example.musicbrainz.setup.UnitTestSetup
import io.mockk.*
import io.mockk.impl.annotations.MockK

abstract class DetailViewModelTestSetup : UnitTestSetup() {

    @MockK
    protected lateinit var mockConnectivity: ConnectivityMonitor
    @MockK
    lateinit var mockResourceProvider: ResourceProvider
    @MockK(relaxed = true)
    protected lateinit var mockInteractorAlbums: InteractorGetAlbums
    @MockK(relaxed = true)
    lateinit var mockObserverAlbums: Observer<AlbumsResult>

    private val artistParser = ArtistMockParser(fileParser)
    private val albumParser = AlbumMockParser(fileParser)

    private val artists = artistParser.getMockArtistsFromFeedWithAllItemsValid()

    private val internetOffError = ArtistsResult.ArtistsError(INTERNET_OFF_MSG)

    private val mockAlbums = albumParser.getMockAlbumsFromFeedWithAllItemsValid()
    private val albumsSuccess = AlbumsResult.AlbumsSuccess(mockAlbums)
    private val albumsError = AlbumsResult.AlbumsError(ERROR_MSG)

    val mockSelectedArtist = artists[0]
    private val albumsQuery = buildAlbumsQuery(mockSelectedArtist.id)

    protected lateinit var subject: DetailViewModel

    companion object {
        const val ERROR_MSG = "error"
        const val INTERNET_OFF_MSG = "please activate internet"
    }

    override fun initialiseClassUnderTest() {
        subject = DetailViewModel(
            mockInteractorAlbums,
            mockConnectivity,
            mockResourceProvider
        )
        every { mockResourceProvider.getInternetOffMsg() } returns INTERNET_OFF_MSG
        every { mockObserverAlbums.onChanged(any()) } just runs
    }

    protected fun mockInternetActive(active: Boolean) {
        every { mockConnectivity.isOnline() } returns active
    }

    protected fun verifyInternetChecked() {
        verify(exactly = 1) { mockConnectivity.isOnline() }
    }

    protected fun initialiseLiveData() {
        subject.albumsResult.observeForever(mockObserverAlbums)
    }

    protected fun mockAlbumsCall() {
        coEvery { mockInteractorAlbums.invoke(albumsQuery) } returns mockAlbums
    }

    protected fun mockAlbumsCallThrowsError() {
        coEvery { mockInteractorAlbums.invoke(albumsQuery) } throws IllegalStateException(ERROR_MSG)
    }

    protected fun verifyAlbumsCallDone() {
        coVerify(exactly = 1) { mockInteractorAlbums.invoke(albumsQuery) }
    }

    protected fun verifyAlbumsCallNotDone() {
        coVerify(exactly = 1) { mockInteractorAlbums.invoke(any()) }
    }

    protected fun verifyResultAlbumsCallSuccess() {
        verify(exactly = 1) { mockObserverAlbums.onChanged(albumsSuccess) }
    }

    protected fun verifyResultAlbumsCallError() {
        verify(exactly = 1) { mockObserverAlbums.onChanged(albumsError) }
    }

}