package com.example.musicbrainz.datasource

import com.example.musicbrainz.domain.Album
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.framework.datasource.network.data.MusicBrainzRemoteDao
import com.example.musicbrainz.framework.datasource.network.data.MusicBrainzRemoteDataSource
import com.example.musicbrainz.parser.AlbumMockParser
import com.example.musicbrainz.parser.ArtistMockParser
import com.example.musicbrainz.setup.UnitTestSetup
import com.example.musicbrainz.util.AlbumVerificator
import com.example.musicbrainz.util.ArtistVerificator
import com.example.musicbrainz.util.verifyListsHaveSameSize
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MusicBrainzRemoteDataSourceTest : UnitTestSetup() {

    @MockK
    private lateinit var mockDao: MusicBrainzRemoteDao

    private val artistsParser = ArtistMockParser(fileParser)
    private val albumParser = AlbumMockParser(fileParser)
    private var artistVerificator = ArtistVerificator()
    private var albumVerificator = AlbumVerificator()
    private var searchQuery = "search query"
    private var albumsQuery = "albums query"

    private lateinit var subject: MusicBrainzRemoteDataSource

    init {
        initialise()
    }

    override fun initialiseClassUnderTest() {
        subject = MusicBrainzRemoteDataSource(mockDao)
    }

    @Test
    fun dataSourceRequestsArtists_then_daoRequestsArtists() {
        // given
        val mockFeed = artistsParser.getMockArtistsFeedAllIdsValid()
        coEvery { mockDao.getArtists(searchQuery) } returns mockFeed

        // when
        lateinit var items: List<Artist>
        runBlocking {
            items = subject.getArtists(searchQuery)
        }

        // then
        coVerify(exactly = 1) { mockDao.getArtists(searchQuery) }
        verifyListsHaveSameSize(items, mockFeed.artists!!)
        artistVerificator.verifyItemsAgainstRemoteFeed(items, mockFeed)
    }

    @Test
    fun dataSourceRequestsAlbums_then_daoRequestsAlbums() {
        runBlocking {
            // given
            val mockFeed = albumParser.getMockAlbumsFeedAllIdsValid()
            coEvery { mockDao.getAlbums(albumsQuery) } returns mockFeed

            // when
            lateinit var items: List<Album>
            runBlocking {
                items = subject.getAlbums(albumsQuery)
            }

            // then
            coVerify(exactly = 1) { mockDao.getAlbums(albumsQuery) }
            verifyListsHaveSameSize(items, mockFeed.releases!!)
            albumVerificator.verifyItemsAgainstRemoteFeed(items, mockFeed)
        }
    }

}