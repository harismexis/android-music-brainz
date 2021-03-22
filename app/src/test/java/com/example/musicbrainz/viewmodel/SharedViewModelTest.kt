package com.example.musicbrainz.viewmodel

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SharedViewModelTest : SharedViewModelTestSetup() {

    init {
        initialise()
    }

    @Before
    fun doBeforeEachTestCase() {
        initialiseLiveData()
    }

    @Test
    fun internetActive_when_searchTriggered_then_resultSuccess() {
        // given
        mockInternetActive(true)
        mockSearchCall()

        // when
        triggerSearch()

        // then
        verifyInternetChecked()
        verifySearchCallDone()
        verifySearchResultSuccess()
    }

    @Test
    fun internetOff_when_searchTriggered_then_resultError() {
        // given
        mockInternetActive(false)

        // when
        triggerSearch()

        // then
        verifyInternetChecked()
        verifySearchCallNotDone()
        verifyResultInternetOff()
    }

    @Test
    fun searchCallThrowsError_when_searchTriggered_then_resultError() {
        // given
        mockInternetActive(true)
        mockSearchCallThrowsError()

        // when
        triggerSearch()

        // then
        verifyInternetChecked()
        verifySearchCallDone()
        verifySearchResultError()
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
    fun remoteCallThrowsError_when_albumsRequested_then_resultError() {
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