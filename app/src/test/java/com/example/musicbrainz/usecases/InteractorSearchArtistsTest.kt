package com.example.musicbrainz.usecases

import com.example.musicbrainz.data.MusicBrainzRemoteRepository
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.setup.BaseUnitTest
import com.example.musicbrainz.util.verifyListsHaveSameSize
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class InteractorSearchArtistsTest : BaseUnitTest() {

    @MockK
    private lateinit var mockRepository: MusicBrainzRemoteRepository

    private lateinit var mockItems: List<Artist>
    private lateinit var subject: InteractorSearchArtists
    private var query = "query"

    override fun initialiseClassUnderTest() {
        setupMocks()
        subject = InteractorSearchArtists(mockRepository)
    }

    private fun setupMocks() {
        mockItems = artistProvider.getMockArtistsFromFeedWithAllItemsValid()
        coEvery { mockRepository.getArtists(query) } returns mockItems
    }

    @Test
    fun interactorInvoked_then_repositoryCallsExpectedMethodWithExpectedArgs() {
        // when
        lateinit var items: List<Artist>
        runBlocking {
            items = subject.invoke(query)
        }

        // then
        coVerify(exactly = 1) { mockRepository.getArtists(query) }
        verifyListsHaveSameSize(mockItems, items)
    }

}