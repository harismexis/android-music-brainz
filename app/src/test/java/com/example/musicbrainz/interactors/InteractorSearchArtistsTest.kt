package com.example.musicbrainz.interactors

import com.example.musicbrainz.data.MusicBrainzRemoteRepository
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.parser.ArtistMockParser
import com.example.musicbrainz.setup.UnitTestSetup
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class InteractorSearchArtistsTest : UnitTestSetup() {

    @MockK
    private lateinit var mockRepository: MusicBrainzRemoteRepository

    private val mockParser = ArtistMockParser(fileParser)
    private lateinit var mockItems: List<Artist>
    private lateinit var subject: InteractorSearchArtists
    private var query = "query"

    init {
        initialise()
    }

    override fun initialiseClassUnderTest() {
        setupMocks()
        subject = InteractorSearchArtists(mockRepository)
    }

    private fun setupMocks() {
        mockItems = mockParser.getMockArtistsFromFeedWithAllItemsValid()
        coEvery { mockRepository.getArtists(query) } returns mockItems
    }

    @Test
    fun interactorInvoked_then_repositoryCallsExpectedMethodWithExpectedArgAndResult() {
        // when
        lateinit var items: List<Artist>
        runBlocking {
            items = subject.invoke(query)
        }

        // then
        coVerify(exactly = 1) { mockRepository.getArtists(query) }
        Assert.assertEquals(mockItems.size, items.size)
    }

}