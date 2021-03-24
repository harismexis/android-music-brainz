package com.example.musicbrainz.detail

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DetailViewModelTest : DetailViewModelTestSetup() {

    init {
        initialise()
    }

    @Before
    fun doBeforeEachTestCase() {
        initialiseLiveData()
    }

    @Test
    fun internetOn_when_albumsRequested_then_resultSuccess() {
        // given
        subject.selectedArtist = mockSelectedArtist
        mockAlbumsCall()
        mockInternetActive(true)

        // when
        subject.fetchAlbums()

        // then
        verifyAlbumsCallDone()
        verifyResultAlbumsCallSuccess()
    }

    @Test
    fun albumsCallThrowsError_when_albumsRequested_then_resultError() {
        // given
        subject.selectedArtist = mockSelectedArtist
        mockInternetActive(true)
        mockAlbumsCallThrowsError()

        // when
        subject.fetchAlbums()

        // then
        verifyAlbumsCallDone()
        verifyResultAlbumsCallError()
    }

}