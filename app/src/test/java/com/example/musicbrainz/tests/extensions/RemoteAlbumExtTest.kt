package com.example.musicbrainz.tests.extensions

import com.example.musicbrainz.base.BaseUnitTest
import com.example.musicbrainz.framework.util.extensions.toItems
import com.example.musicbrainz.reader.MockAlbumProvider.Companion.EXPECTED_NUM_ALBUMS_WHEN_ALL_IDS_VALID
import com.example.musicbrainz.reader.MockAlbumProvider.Companion.EXPECTED_NUM_ALBUMS_WHEN_NO_DATA
import com.example.musicbrainz.reader.MockAlbumProvider.Companion.EXPECTED_NUM_ALBUMS_WHEN_SOME_EMPTY
import com.example.musicbrainz.reader.MockAlbumProvider.Companion.EXPECTED_NUM_ALBUMS_WHEN_SOME_IDS_INVALID
import com.example.musicbrainz.util.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RemoteAlbumExtTest : BaseUnitTest() {

    private val verificator = AlbumVerificator()

    @Test
    fun responseHasAllItemsValid_then_conversionToItemsIsCorrect() {
        // given
        val response = albumProvider.getMockAlbumsFeedAllIdsValid()

        // when
        val items = response.toItems()

        // then
        verifyListsHaveSameSize(response.releases!!, items)
        verifyListSizeWhenAllIdsValid(response.releases!!, EXPECTED_NUM_ALBUMS_WHEN_ALL_IDS_VALID)
        verifyListSizeWhenAllIdsValid(items, EXPECTED_NUM_ALBUMS_WHEN_ALL_IDS_VALID)
        verificator.verifyItemsAgainstResponse(items, response)
    }

    @Test
    fun responseHasSomeIdsAbsent_then_conversionToItemsIsCorrect() {
        // given
        val response = albumProvider.getMockAlbumsFeedSomeIdsInvalid()

        // when
        val items = response.toItems()

        // then
        verifyListSizeWhenSomeIdsAbsent(items, EXPECTED_NUM_ALBUMS_WHEN_SOME_IDS_INVALID)
        verificator.verifyItemsAgainstResponse(items, response)
    }

    @Test
    fun responseHasSomeEmptyItems_then_conversionToItemsIsCorrect() {
        // given
        val response = albumProvider.getMockAlbumsFeedSomeItemsEmpty()

        // when
        val items = response.toItems()

        // then
        verifyListSizeWhenSomeItemsEmpty(items, EXPECTED_NUM_ALBUMS_WHEN_SOME_EMPTY)
        verificator.verifyItemsAgainstResponse(items, response)
    }

    @Test
    fun responseHasAllIdsAbsent_then_itemListIsEmpty() {
        // given
        val response = albumProvider.getMockAlbumsFeedAllIdsInvalid()

        // when
        val items = response.toItems()

        // then
        verifyListSizeForNoData(items, EXPECTED_NUM_ALBUMS_WHEN_NO_DATA)
    }

    @Test
    fun responseIsAnEmptyJson_then_itemListIsEmpty() {
        // given
        val response = albumProvider.getMockAlbumsFeedEmptyJson()

        // when
        val items = response.toItems()

        // then
        verifyListSizeForNoData(items, EXPECTED_NUM_ALBUMS_WHEN_NO_DATA)
    }

}
