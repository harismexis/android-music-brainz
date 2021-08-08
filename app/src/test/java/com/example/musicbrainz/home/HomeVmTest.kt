package com.example.musicbrainz.home

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HomeVmTest : HomeVmBaseTest() {

    @Test
    fun searchCallSuccessful_searchResultSuccess() {
        // given
        mockSearchCall()

        // when
        search()

        // then
        verifySearchCallDone()
        verifySearchResultSuccess()
    }

    @Test
    fun searchCallThrows_searchResultError() {
        // given
        mockSearchCallThrows()

        // when
        search()

        // then
        verifySearchCallDone()
        verifySearchResultError()
    }

}