package com.example.musicbrainz.detail

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DetailVmTest : DetailVmBaseTest() {

    init {
        initialise()
    }

    @Before
    fun doBeforeEachTestCase() {
        initialiseLiveData()
    }

    @Test
    fun albumsCallSuccessful_when_albumsRequested_then_resultSuccess() {
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

    @Test
    fun internetOff_when_albumsRequested_then_resultError() {
        // given
        mockInternetActive(false)

        // when
        subject.fetchAlbums()

        // then
        verifyInternetChecked()
        verifyAlbumsCallNotDone()
        verifyResultInternetOff()
    }

}