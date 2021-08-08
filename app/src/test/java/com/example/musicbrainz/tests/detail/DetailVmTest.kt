package com.example.musicbrainz.tests.detail

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DetailVmTest : DetailVmBaseTest() {

    @Test
    fun albumsCallSuccessful_albumsResultSuccess() {
        // given
        subject.artist = mockSelectedArtist
        mockAlbumsCall()

        // when
        subject.fetchAlbums()

        // then
        verifyAlbumsCallDone()
        verifyResultAlbumsCallSuccess()
    }

    @Test
    fun albumsCallThrows_albumsResultError() {
        // given
        subject.artist = mockSelectedArtist
        mockAlbumsCallThrows()

        // when
        subject.fetchAlbums()

        // then
        verifyAlbumsCallDone()
        verifyResultAlbumsCallError()
    }

}