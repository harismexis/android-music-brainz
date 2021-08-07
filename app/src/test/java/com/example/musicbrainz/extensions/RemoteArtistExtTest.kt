package com.example.musicbrainz.extensions

import com.example.musicbrainz.framework.util.extensions.toItems
import com.example.musicbrainz.reader.MockArtistProvider.Companion.EXPECTED_NUM_ARTISTS_WHEN_ALL_IDS_VALID
import com.example.musicbrainz.reader.MockArtistProvider.Companion.EXPECTED_NUM_ARTISTS_WHEN_NO_DATA
import com.example.musicbrainz.reader.MockArtistProvider.Companion.EXPECTED_NUM_ARTISTS_WHEN_SOME_EMPTY
import com.example.musicbrainz.reader.MockArtistProvider.Companion.EXPECTED_NUM_ARTISTS_WHEN_SOME_IDS_INVALID
import com.example.musicbrainz.setup.BaseUnitTest
import com.example.musicbrainz.util.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RemoteArtistExtTest : BaseUnitTest() {

    private val verificator = ArtistVerificator()

    @Test
    fun feedHasAllItemsValid_then_conversionToItemsIsCorrect() {
        // given
        val remoteFeed = artistProvider.getMockArtistsFeedAllIdsValid()

        // when
        val items = remoteFeed.toItems()

        // then
        verifyListsHaveSameSize(remoteFeed.artists!!, items)
        verifyListSizeWhenAllIdsValid(remoteFeed.artists!!, EXPECTED_NUM_ARTISTS_WHEN_ALL_IDS_VALID)
        verifyListSizeWhenAllIdsValid(items, EXPECTED_NUM_ARTISTS_WHEN_ALL_IDS_VALID)
        verificator.verifyItemsAgainstRemoteFeed(items, remoteFeed)
    }

    @Test
    fun feedHasSomeIdsAbsent_then_conversionToItemsIsCorrect() {
        // given
        val remoteFeed = artistProvider.getMockArtistsFeedSomeIdsAbsent()

        // when
        val items = remoteFeed.toItems()

        // then
        verifyListSizeWhenSomeIdsAbsent(items, EXPECTED_NUM_ARTISTS_WHEN_SOME_IDS_INVALID)
        verificator.verifyItemsAgainstRemoteFeed(items, remoteFeed)
    }

    @Test
    fun feedHasSomeEmptyItems_then_conversionToItemsIsCorrect() {
        // given
        val remoteFeed = artistProvider.getMockArtistsFeedSomeItemsEmpty()

        // when
        val items = remoteFeed.toItems()

        // then
        verifyListSizeWhenSomeItemsEmpty(items, EXPECTED_NUM_ARTISTS_WHEN_SOME_EMPTY)
        verificator.verifyItemsAgainstRemoteFeed(items, remoteFeed)
    }

    @Test
    fun feedHasAllIdsAbsent_then_itemListIsEmpty() {
        // given
        val remoteFeed = artistProvider.getMockArtistsFeedAllIdsAbsent()

        // when
        val items = remoteFeed.toItems()

        // then
        verifyListSizeForNoData(items, EXPECTED_NUM_ARTISTS_WHEN_NO_DATA)
    }

    @Test
    fun feedIsAnEmptyJson_then_itemListIsEmpty() {
        // given
        val remoteFeed = artistProvider.getMockArtistsFeedEmptyJson()

        // when
        val items = remoteFeed.toItems()

        // then
        verifyListSizeForNoData(items, EXPECTED_NUM_ARTISTS_WHEN_NO_DATA)
    }

}
