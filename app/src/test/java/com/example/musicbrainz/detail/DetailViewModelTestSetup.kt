package com.example.musicbrainz.detail

import androidx.lifecycle.Observer
import com.example.musicbrainz.framework.util.ConnectivityMonitor
import com.example.musicbrainz.framework.util.buildAlbumsQuery
import com.example.musicbrainz.framework.util.resource.ResourceProvider
import com.example.musicbrainz.interactors.InteractorGetAlbums
import com.example.musicbrainz.presentation.screens.detail.viewmodel.DetailViewModel
import com.example.musicbrainz.reader.MockAlbumProvider
import com.example.musicbrainz.reader.MockArtistProvider
import com.example.musicbrainz.setup.BaseUnitTest
import com.example.musicbrainz.util.result.AlbumsResult
import io.mockk.*
import io.mockk.impl.annotations.MockK

abstract class DetailViewModelTestSetup : BaseUnitTest() {

    @MockK
    protected lateinit var mockConnectivity: ConnectivityMonitor
    @MockK
    protected lateinit var mockResourceProvider: ResourceProvider
    @MockK
    protected lateinit var mockInteractorAlbums: InteractorGetAlbums
    @MockK
    protected lateinit var mockObserverAlbums: Observer<AlbumsResult>

    private val artistParser = MockArtistProvider(fileParser)
    val mockSelectedArtist = artistParser.getMockArtist()

    private val albumParser = MockAlbumProvider(fileParser)
    private val mockAlbums = albumParser.getMockAlbumsFromFeedWithAllItemsValid()
    private val albumsSuccess = AlbumsResult.Success(mockAlbums)
    private val albumsError = AlbumsResult.Error(ERROR_MSG)
    private val internetOffError = AlbumsResult.Error(INTERNET_OFF_MSG)
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
        coVerify(exactly = 0) { mockInteractorAlbums.invoke(any()) }
    }

    protected fun verifyResultAlbumsCallSuccess() {
        verify(exactly = 1) { mockObserverAlbums.onChanged(albumsSuccess) }
    }

    protected fun verifyResultAlbumsCallError() {
        verify(exactly = 1) { mockObserverAlbums.onChanged(albumsError) }
    }

    protected fun verifyResultInternetOff() {
        verify(exactly = 1) { mockObserverAlbums.onChanged(internetOffError) }
    }

}