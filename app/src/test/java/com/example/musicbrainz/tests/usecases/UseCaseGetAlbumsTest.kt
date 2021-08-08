package com.example.musicbrainz.tests.usecases

import com.example.musicbrainz.base.BaseUnitTest
import com.example.musicbrainz.data.MusicBrainzRemoteRepository
import com.example.musicbrainz.domain.Album
import com.example.musicbrainz.usecases.UseCaseGetAlbums
import com.example.musicbrainz.util.verifyListsHaveSameSize
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UseCaseGetAlbumsTest : BaseUnitTest() {

    @MockK
    private lateinit var mockRepository: MusicBrainzRemoteRepository

    private lateinit var mockItems: List<Album>
    private lateinit var subject: UseCaseGetAlbums
    private var query = "query"

    override fun onDoBefore() {
        setupMocks()
        subject = UseCaseGetAlbums(mockRepository)
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