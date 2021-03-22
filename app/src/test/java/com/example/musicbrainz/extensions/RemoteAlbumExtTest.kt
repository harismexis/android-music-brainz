package com.example.musicbrainz.extensions

import com.example.musicbrainz.framework.extensions.toItems
import com.example.musicbrainz.parser.AlbumMockParser
import com.example.musicbrainz.parser.AlbumMockParser.Companion.EXPECTED_NUM_ALBUMS_WHEN_ALL_IDS_VALID
import com.example.musicbrainz.parser.AlbumMockParser.Companion.EXPECTED_NUM_ALBUMS_WHEN_NO_DATA
import com.example.musicbrainz.parser.AlbumMockParser.Companion.EXPECTED_NUM_ALBUMS_WHEN_TWO_EMPTY
import com.example.musicbrainz.parser.AlbumMockParser.Companion.EXPECTED_NUM_ALBUMS_WHEN_TWO_IDS_ABSENT
import com.example.musicbrainz.setup.UnitTestSetup
import com.example.musicbrainz.utils.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RemoteAlbumExtTest : UnitTestSetup() {

    private val verificator = AlbumVerificator()
    private val mockParser = AlbumMockParser(fileParser)

    @Test
    fun feedHasAllItemsValid_then_conversionToItemsIsCorrect() {
        // given
        val remoteFeed = mockParser.getMockAlbumsFeedAllIdsValid()

        // when
        val items = remoteFeed.toItems()

        // then
        verifyListsHaveSameSize(remoteFeed.releases!!, items)
        verifyListSizeWhenAllIdsValid(remoteFeed.releases!!, EXPECTED_NUM_ALBUMS_WHEN_ALL_IDS_VALID)
        verifyListSizeWhenAllIdsValid(items, EXPECTED_NUM_ALBUMS_WHEN_ALL_IDS_VALID)
        verificator.verifyItemsAgainstRemoteFeed(items, remoteFeed)
    }

    @Test
    fun feedHasSomeIdsAbsent_then_conversionToItemsIsCorrect() {
        // given
        val remoteFeed = mockParser.getMockAlbumsFeedSomeIdsAbsent()

        // when
        val items = remoteFeed.toItems()

        // then
        verifyListSizeWhenSomeIdsAbsent(items, EXPECTED_NUM_ALBUMS_WHEN_TWO_IDS_ABSENT)
        verificator.verifyItemsAgainstRemoteFeed(items, remoteFeed)
    }

    @Test
    fun feedHasSomeEmptyItems_then_conversionToItemsIsCorrect() {
        // given
        val remoteFeed = mockParser.getMockAlbumsFeedSomeItemsEmpty()

        // when
        val items = remoteFeed.toItems()

        // then
        verifyListSizeWhenSomeItemsEmpty(items, EXPECTED_NUM_ALBUMS_WHEN_TWO_EMPTY)
        verificator.verifyItemsAgainstRemoteFeed(items, remoteFeed)
    }

    @Test
    fun feedHasAllIdsAbsent_then_itemListIsEmpty() {
        // given
        val remoteFeed = mockParser.getMockAlbumsFeedAllIdsAbsent()

        // when
        val items = remoteFeed.toItems()

        // then
        verifyListSizeForNoData(items, EXPECTED_NUM_ALBUMS_WHEN_NO_DATA)
    }

    @Test
    fun feedIsAnEmptyJson_then_itemListIsEmpty() {
        // given
        val remoteFeed = mockParser.getMockAlbumsFeedEmptyJson()

        // when
        val items = remoteFeed.toItems()

        // then
        verifyListSizeForNoData(items, EXPECTED_NUM_ALBUMS_WHEN_NO_DATA)
    }

    override fun initialiseClassUnderTest() {
        // Do nothing
    }

}
