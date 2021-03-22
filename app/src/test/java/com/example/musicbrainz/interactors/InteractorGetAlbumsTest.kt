package com.example.musicbrainz.interactors

import com.example.musicbrainz.data.MusicBrainzRemoteRepository
import com.example.musicbrainz.domain.Album
import com.example.musicbrainz.parser.AlbumMockParser
import com.example.musicbrainz.setup.UnitTestSetup
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class InteractorGetAlbumsTest : UnitTestSetup() {

    @Mock
    private lateinit var mockRepository: MusicBrainzRemoteRepository

    private val mockParser = AlbumMockParser(fileParser)
    private lateinit var mockItems: List<Album>
    private lateinit var subject: InteractorGetAlbums
    private var query = "query"

    init {
        initialise()
    }

    override fun initialiseClassUnderTest() {
        MockitoAnnotations.initMocks(this)
        setupMocks()
        subject = InteractorGetAlbums(mockRepository)
    }

    private fun setupMocks() {
        mockItems = mockParser.getMockAlbumsFromFeedWithAllItemsValid()
        runBlocking {
            Mockito.`when`(mockRepository.getAlbums(query)).thenReturn(mockItems)
        }
    }

    @Test
    fun interactorInvoked_then_repositoryCallsExpectedMethodWithExpectedArgAndResult() =
        runBlocking {
            // when
            val items = subject.invoke(query)

            // then
            verify(mockRepository, times(1)).getAlbums(query)
            Assert.assertEquals(mockItems.size, items.size)
        }

}