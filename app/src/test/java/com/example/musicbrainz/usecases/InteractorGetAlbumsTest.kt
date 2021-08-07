package com.example.musicbrainz.usecases

import com.example.musicbrainz.data.MusicBrainzRemoteRepository
import com.example.musicbrainz.domain.Album
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
class InteractorGetAlbumsTest : BaseUnitTest() {

    @MockK
    private lateinit var mockRepository: MusicBrainzRemoteRepository

    private lateinit var mockItems: List<Album>
    private lateinit var subject: InteractorGetAlbums
    private var query = "query"

    override fun onDoBefore() {
        setupMocks()
        subject = InteractorGetAlbums(mockRepository)
    }

    private fun setupMocks() {
        mockItems = albumProvider.getMockAlbumsFromFeedWithAllItemsValid()
        coEvery { mockRepository.getAlbums(query) } returns mockItems
    }

    @Test
    fun interactorInvoked_then_repositoryCallsExpectedMethodWithExpectedArgs() {
        // when
        lateinit var items: List<Album>
        runBlocking {
            items = subject.invoke(query)
        }

        // then
        coVerify(exactly = 1) { mockRepository.getAlbums(query) }
        verifyListsHaveSameSize(mockItems, items)
    }

}