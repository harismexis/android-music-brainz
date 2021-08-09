package com.example.musicbrainz.tests.usecases

import com.example.musicbrainz.base.BaseUnitTest
import com.example.musicbrainz.data.repository.MusicBrainzRemoteRepository
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.usecases.UseCaseSearchArtists
import com.example.musicbrainz.util.verifyListsHaveSameSize
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UseCaseSearchArtistsTest : BaseUnitTest() {

    @MockK
    private lateinit var mockRepository: MusicBrainzRemoteRepository

    private lateinit var mockItems: List<Artist>
    private lateinit var subject: UseCaseSearchArtists
    private var query = "query"

    override fun onDoBefore() {
        setupMocks()
        subject = UseCaseSearchArtists(mockRepository)
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