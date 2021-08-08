package com.example.musicbrainz.detail

import androidx.lifecycle.Observer
import com.example.musicbrainz.framework.util.formatAlbumsQuery
import com.example.musicbrainz.framework.util.resource.ResourceProvider
import com.example.musicbrainz.presentation.screens.detail.viewmodel.DetailVm
import com.example.musicbrainz.setup.BaseUnitTest
import com.example.musicbrainz.usecases.UseCaseGetAlbums
import com.example.musicbrainz.util.result.AlbumsResult
import io.mockk.*
import io.mockk.impl.annotations.MockK

abstract class DetailVmBaseTest : BaseUnitTest() {

    @MockK
    protected lateinit var mockResourceProvider: ResourceProvider
    @MockK
    protected lateinit var mockUseCaseAlbums: UseCaseGetAlbums
    @MockK
    protected lateinit var mockObserverAlbums: Observer<AlbumsResult>
    protected val mockSelectedArtist = artistProvider.getMockArtist()
    private val mockAlbums = albumProvider.getMockAlbumsFromFeedWithAllItemsValid()
    private val albumsSuccess = AlbumsResult.Success(mockAlbums)
    private val error = Exception(ERROR_MSG)
    private val albumsError = AlbumsResult.Error(error)
    private val albumsQuery = formatAlbumsQuery(mockSelectedArtist.id)

    protected lateinit var subject: DetailVm

    companion object {
        const val ERROR_MSG = "error"
        const val INTERNET_OFF_MSG = "please activate internet"
    }

    override fun onDoBefore() {
        initClassUnderTest()
        initialiseLiveData()
    }

    private fun initClassUnderTest() {
        subject = DetailVm(
            mockUseCaseAlbums,
            mockResourceProvider
        )
        every { mockResourceProvider.getInternetOffMsg() } returns INTERNET_OFF_MSG
        every { mockObserverAlbums.onChanged(any()) } just runs
    }

    protected fun initialiseLiveData() {
        subject.albums.observeForever(mockObserverAlbums)
    }

    protected fun mockAlbumsCall() {
        coEvery { mockUseCaseAlbums(albumsQuery) } returns mockAlbums
    }

    protected fun mockAlbumsCallThrows() {
        coEvery { mockUseCaseAlbums(albumsQuery) } throws error
    }

    protected fun verifyAlbumsCallDone() {
        coVerify(exactly = 1) { mockUseCaseAlbums(albumsQuery) }
    }

    protected fun verifyAlbumsCallNotDone() {
        coVerify(exactly = 0) { mockUseCaseAlbums(any()) }
    }

    protected fun verifyResultAlbumsCallSuccess() {
        verify(exactly = 1) { mockObserverAlbums.onChanged(albumsSuccess) }
    }

    protected fun verifyResultAlbumsCallError() {
        verify(exactly = 1) { mockObserverAlbums.onChanged(albumsError) }
    }

}