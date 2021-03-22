package com.example.musicbrainz.datasource

import com.example.musicbrainz.framework.datasource.network.data.MusicBrainzRemoteDao
import com.example.musicbrainz.framework.datasource.network.data.MusicBrainzRemoteDataSource
import com.example.musicbrainz.parser.AlbumMockParser
import com.example.musicbrainz.parser.ArtistMockParser
import com.example.musicbrainz.setup.UnitTestSetup
import com.example.musicbrainz.utils.AlbumVerificator
import com.example.musicbrainz.utils.ArtistVerificator
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito

@RunWith(JUnit4::class)
class MusicBrainzRemoteDataSourceTest : UnitTestSetup() {

    @Mock
    private lateinit var mockDao: MusicBrainzRemoteDao

    private val artistsParser = ArtistMockParser(fileParser)
    private val albumParser = AlbumMockParser(fileParser)
    private var artistVerificator = ArtistVerificator()
    private var albumVerificator = AlbumVerificator()
    private lateinit var subject: MusicBrainzRemoteDataSource
    private var queryArtists = "test query"
    private var queryAlbums = "test query 2"

    init {
        initialise()
    }

    override fun initialiseClassUnderTest() {
        subject = MusicBrainzRemoteDataSource(mockDao)
    }

    @Test
    fun dataSourceRequestsArtists_then_daoRequestsArtists() {
        runBlocking {
            // given
            val mockFeed = artistsParser.getMockArtistsFeedAllIdsValid()
            Mockito.`when`(mockDao.getArtists(queryArtists)).thenReturn(mockFeed)

            // when
            val items = subject.getArtists(queryArtists)

            // then
            verify(mockDao, times(1)).getArtists(queryArtists)
            artistVerificator.verifyItemsAgainstRemoteFeed(items, mockFeed)
        }
    }

    @Test
    fun dataSourceRequestsAlbums_then_daoRequestsAlbums() {
        runBlocking {
            // given
            val mockFeed = albumParser.getMockAlbumsFeedAllIdsValid()
            Mockito.`when`(mockDao.getAlbums(queryAlbums)).thenReturn(mockFeed)

            // when
            val items = subject.getAlbums(queryAlbums)

            // then
            verify(mockDao, times(1)).getAlbums(queryAlbums)
            albumVerificator.verifyItemsAgainstRemoteFeed(items, mockFeed)
        }
    }

}