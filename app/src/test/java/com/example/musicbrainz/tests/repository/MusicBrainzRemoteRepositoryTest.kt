package com.example.musicbrainz.tests.repository

import com.example.musicbrainz.base.BaseUnitTest
import com.example.musicbrainz.data.datasource.MusicBrainzBaseRemoteDataSource
import com.example.musicbrainz.data.repository.MusicBrainzRemoteRepository
import com.example.musicbrainz.domain.Album
import com.example.musicbrainz.domain.Artist
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MusicBrainzRemoteRepositoryTest : BaseUnitTest() {

    @MockK
    private lateinit var mockDataSource: MusicBrainzBaseRemoteDataSource

    private var searchQuery = "search query"
    private var albumsQuery = "albums query"

    private lateinit var subject: MusicBrainzRemoteRepository

    override fun onDoBefore() {
        subject = MusicBrainzRemoteRepository(mockDataSource)
    }

    @Test
    fun repositoryRequestsArtists_dataSourceReturnsExpectedItems() {
        // given
        val mockArtists = artistProvider.getMockArtistsFromFeedWithAllIdsInvalid()
        coEvery { mockDataSource.getArtists(searchQuery) } returns mockArtists

        // when
        lateinit var items: List<Artist>
        runBlocking {
            items = subject.getArtists(searchQuery)
        }

        // then
        coVerify(exactly = 1) { mockDataSource.getArtists(searchQuery) }
        Assert.assertEquals(items, mockArtists)
    }

    @Test
    fun repositoryRequestsAlbums_dataSourceReturnsExpectedItems() {
        runBlocking {
            // given
            val mockAlbums = albumProvider.getMockAlbumsFromFeedWithAllIdsInvalid()
            coEvery { mockDataSource.getAlbums(albumsQuery) } returns mockAlbums

            // when
            lateinit var items: List<Album>
            runBlocking {
                items = subject.getAlbums(albumsQuery)
            }

            // then
            coVerify(exactly = 1) { mockDataSource.getAlbums(albumsQuery) }
            Assert.assertEquals(items, mockAlbums)
        }
    }

}