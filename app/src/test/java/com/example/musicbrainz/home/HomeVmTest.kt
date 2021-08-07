package com.example.musicbrainz.home

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HomeVmTest : HomeVmBaseTest() {

    init {
        initialise()
    }

    @Before
    fun doBeforeEachTestCase() {
        initialiseLiveData()
    }

    @Test
    fun searchCallSuccessful_when_searchArtistTriggered_then_resultSuccess() {
        // given
        mockInternetActive(true)
        mockSearchCall()

        // when
        triggerSearchArtist()

        // then
        verifyInternetChecked()
        verifySearchCallDone()
        verifySearchResultSuccess()
    }

    @Test
    fun searchCallThrowsError_when_searchTriggered_then_resultError() {
        // given
        mockInternetActive(true)
        mockSearchCallThrowsError()

        // when
        triggerSearchArtist()

        // then
        verifyInternetChecked()
        verifySearchCallDone()
        verifySearchResultError()
    }

    @Test
    fun internetOff_when_searchTriggered_then_resultError() {
        // given
        mockInternetActive(false)

        // when
        triggerSearchArtist()

        // then
        verifyInternetChecked()
        verifySearchCallNotDone()
        verifyResultInternetOff()
    }

}