package com.example.musicbrainz.extensions

import com.example.musicbrainz.framework.extensions.toItems
import com.example.musicbrainz.parser.ArtistMockParser
import com.example.musicbrainz.parser.ArtistMockParser.Companion.EXPECTED_NUM_ARTISTS_WHEN_ALL_IDS_VALID
import com.example.musicbrainz.parser.ArtistMockParser.Companion.EXPECTED_NUM_ARTISTS_WHEN_NO_DATA
import com.example.musicbrainz.parser.ArtistMockParser.Companion.EXPECTED_NUM_ARTISTS_WHEN_SOME_EMPTY
import com.example.musicbrainz.parser.ArtistMockParser.Companion.EXPECTED_NUM_ARTISTS_WHEN_SOME_IDS_INVALID
import com.example.musicbrainz.setup.UnitTestSetup
import com.example.musicbrainz.util.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RemoteArtistExtTest : UnitTestSetup() {

    private val verificator = ArtistVerificator()
    private val mockParser = ArtistMockParser(fileParser)

    @Test
    fun feedHasAllItemsValid_then_conversionToItemsIsCorrect() {
        // given
        val remoteFeed = mockParser.getMockArtistsFeedAllIdsValid()

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
        val remoteFeed = mockParser.getMockArtistsFeedSomeIdsAbsent()

        // when
        val items = remoteFeed.toItems()

        // then
        verifyListSizeWhenSomeIdsAbsent(items, EXPECTED_NUM_ARTISTS_WHEN_SOME_IDS_INVALID)
        verificator.verifyItemsAgainstRemoteFeed(items, remoteFeed)
    }

    @Test
    fun feedHasSomeEmptyItems_then_conversionToItemsIsCorrect() {
        // given
        val remoteFeed = mockParser.getMockArtistsFeedSomeItemsEmpty()

        // when
        val items = remoteFeed.toItems()

        // then
        verifyListSizeWhenSomeItemsEmpty(items, EXPECTED_NUM_ARTISTS_WHEN_SOME_EMPTY)
        verificator.verifyItemsAgainstRemoteFeed(items, remoteFeed)
    }

    @Test
    fun feedHasAllIdsAbsent_then_itemListIsEmpty() {
        // given
        val remoteFeed = mockParser.getMockArtistsFeedAllIdsAbsent()

        // when
        val items = remoteFeed.toItems()

        // then
        verifyListSizeForNoData(items, EXPECTED_NUM_ARTISTS_WHEN_NO_DATA)
    }

    @Test
    fun feedIsAnEmptyJson_then_itemListIsEmpty() {
        // given
        val remoteFeed = mockParser.getMockArtistsFeedEmptyJson()

        // when
        val items = remoteFeed.toItems()

        // then
        verifyListSizeForNoData(items, EXPECTED_NUM_ARTISTS_WHEN_NO_DATA)
    }

    override fun initialiseClassUnderTest() {
        // Do nothing
    }

}
