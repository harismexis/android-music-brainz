package com.example.musicbrainz.viewmodel

import androidx.lifecycle.Observer
import com.example.musicbrainz.framework.resource.ResourceProvider
import com.example.musicbrainz.framework.util.ConnectivityMonitor
import com.example.musicbrainz.interactors.InteractorGetAlbums
import com.example.musicbrainz.interactors.InteractorSearchArtists
import com.example.musicbrainz.parser.AlbumMockParser
import com.example.musicbrainz.parser.ArtistMockParser
import com.example.musicbrainz.presentation.result.AlbumsResult
import com.example.musicbrainz.presentation.result.ArtistsResult
import com.example.musicbrainz.presentation.viewmodel.SharedViewModel
import com.example.musicbrainz.setup.UnitTestSetup
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`

abstract class SharedViewModelTestSetup : UnitTestSetup() {

    @Mock
    protected lateinit var mockIrrSearchArtists: InteractorSearchArtists
    @Mock
    protected lateinit var mockConnectivity: ConnectivityMonitor
    @Mock
    lateinit var mockObserverArtists: Observer<ArtistsResult>
    @Mock
    lateinit var mockResourceProvider: ResourceProvider
    @Mock
    protected lateinit var mockInteractorAlbums: InteractorGetAlbums
    @Mock
    lateinit var mockObserverAlbums: Observer<AlbumsResult>

    private val artistParser = ArtistMockParser(fileParser)
    private val albumParser = AlbumMockParser(fileParser)

    private val artists = artistParser.getMockArtistsFromFeedWithAllItemsValid()
    private val artistsSuccess = ArtistsResult.ArtistsSuccess(artists)
    private val artistsError = ArtistsResult.ArtistsError(ERROR_MSG)
    private val internetOffError = ArtistsResult.ArtistsError(INTERNET_OFF_MSG)

    private val mockAlbums = albumParser.getMockAlbumsFromFeedWithAllItemsValid()
    private val albumsSuccess = AlbumsResult.AlbumsSuccess(mockAlbums)
    private val albumsError = AlbumsResult.AlbumsError(ERROR_MSG)

    val mockSelectedArtist = artists[0]
    private var artistName = "Rory Gallagher"
    lateinit var albumsQuery: String

    protected lateinit var subject: SharedViewModel

    companion object {
        const val ERROR_MSG = "error"
        const val INTERNET_OFF_MSG = "please activate internet"
    }

    override fun initialiseClassUnderTest() {
        subject = SharedViewModel(
            mockIrrSearchArtists,
            mockInteractorAlbums,
            mockConnectivity,
            mockResourceProvider
        )
        `when`(mockResourceProvider.getInternetOffMsg()).thenReturn(INTERNET_OFF_MSG)
        val selectedArtistId = mockSelectedArtist.id
        albumsQuery = "arid:$selectedArtistId"
    }

    protected fun triggerSearch() {
        subject.searchQuery = artistName
    }

    protected fun mockInternetActive(active: Boolean) {
        `when`(mockConnectivity.isOnline()).thenReturn(active)
    }

    protected fun verifyInternetChecked() {
        verify(mockConnectivity, times(1)).isOnline()
    }

    protected fun mockSearchCall() = runBlocking {
        `when`(mockIrrSearchArtists.invoke(artistName)).thenReturn(artists)
    }

    protected fun mockSearchCallThrowsError() = runBlocking {
        `when`(mockIrrSearchArtists.invoke(artistName)).thenThrow(
            IllegalStateException(
                ERROR_MSG
            )
        )
    }

    protected fun verifySearchCallDone() = runBlocking {
        verify(mockIrrSearchArtists, Mockito.times(1)).invoke(artistName)
    }

    protected fun verifySearchCallNotDone() = runBlocking {
        verify(mockIrrSearchArtists, Mockito.never()).invoke(any())
    }

    protected fun initialiseLiveData() {
        subject.artistsResult.observeForever(mockObserverArtists)
        subject.albumsResult.observeForever(mockObserverAlbums)
    }

    protected fun verifySearchResultSuccess() {
        verify(mockObserverArtists).onChanged(artistsSuccess)
    }

    protected fun verifySearchResultError() {
        verify(mockObserverArtists).onChanged(artistsError)
    }

    protected fun verifyResultInternetOff() {
        verify(mockObserverArtists).onChanged(internetOffError)
    }

    // -------

    protected fun mockAlbumsCall() {
        runBlocking {
            `when`(mockInteractorAlbums.invoke(albumsQuery)).thenReturn(mockAlbums)
        }
    }

    protected fun mockAlbumsCallThrowsError() {
        runBlocking {
            `when`(mockInteractorAlbums.invoke(albumsQuery))
                .thenThrow(IllegalStateException(ERROR_MSG))
        }
    }

    protected fun verifyAlbumsCallDone() = runBlocking {
        verify(mockInteractorAlbums, Mockito.times(1)).invoke(albumsQuery)
    }

    protected fun verifyAlbumsCallNotDone() = runBlocking {
        verify(mockInteractorAlbums, Mockito.never()).invoke(any())
    }

    protected fun verifyResultAlbumsCallSuccess() {
        verify(mockObserverAlbums).onChanged(albumsSuccess)
    }

    protected fun verifyResultAlbumsCallError() {
        verify(mockObserverAlbums).onChanged(albumsError)
    }

}