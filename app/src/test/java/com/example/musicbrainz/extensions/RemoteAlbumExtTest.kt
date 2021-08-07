package com.example.musicbrainz.extensions

import com.example.musicbrainz.framework.util.extensions.toItems
import com.example.musicbrainz.reader.MockAlbumProvider.Companion.EXPECTED_NUM_ALBUMS_WHEN_ALL_IDS_VALID
import com.example.musicbrainz.reader.MockAlbumProvider.Companion.EXPECTED_NUM_ALBUMS_WHEN_NO_DATA
import com.example.musicbrainz.reader.MockAlbumProvider.Companion.EXPECTED_NUM_ALBUMS_WHEN_SOME_EMPTY
import com.example.musicbrainz.reader.MockAlbumProvider.Companion.EXPECTED_NUM_ALBUMS_WHEN_SOME_IDS_INVALID
import com.example.musicbrainz.setup.BaseUnitTest
import com.example.musicbrainz.util.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RemoteAlbumExtTest : BaseUnitTest() {

    private val verificator = AlbumVerificator()

    @Test
    fun feedHasAllItemsValid_then_conversionToItemsIsCorrect() {
        // given
        val remoteFeed = albumProvider.getMockAlbumsFeedAllIdsValid()

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
        val remoteFeed = albumProvider.getMockAlbumsFeedSomeIdsInvalid()

        // when
        val items = remoteFeed.toItems()

        // then
        verifyListSizeWhenSomeIdsAbsent(items, EXPECTED_NUM_ALBUMS_WHEN_SOME_IDS_INVALID)
        verificator.verifyItemsAgainstRemoteFeed(items, remoteFeed)
    }

    @Test
    fun feedHasSomeEmptyItems_then_conversionToItemsIsCorrect() {
        // given
        val remoteFeed = albumProvider.getMockAlbumsFeedSomeItemsEmpty()

        // when
        val items = remoteFeed.toItems()

        // then
        verifyListSizeWhenSomeItemsEmpty(items, EXPECTED_NUM_ALBUMS_WHEN_SOME_EMPTY)
        verificator.verifyItemsAgainstRemoteFeed(items, remoteFeed)
    }

    @Test
    fun feedHasAllIdsAbsent_then_itemListIsEmpty() {
        // given
        val remoteFeed = albumProvider.getMockAlbumsFeedAllIdsInvalid()

        // when
        val items = remoteFeed.toItems()

        // then
        verifyListSizeForNoData(items, EXPECTED_NUM_ALBUMS_WHEN_NO_DATA)
    }

    @Test
    fun feedIsAnEmptyJson_then_itemListIsEmpty() {
        // given
        val remoteFeed = albumProvider.getMockAlbumsFeedEmptyJson()

        // when
        val items = remoteFeed.toItems()

        // then
        verifyListSizeForNoData(items, EXPECTED_NUM_ALBUMS_WHEN_NO_DATA)
    }

    override fun initialiseClassUnderTest() {
        // Do nothing
    }

}
