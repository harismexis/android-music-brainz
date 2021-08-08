package com.example.musicbrainz.tests.extensions

import com.example.musicbrainz.base.BaseUnitTest
import com.example.musicbrainz.framework.util.extensions.toItems
import com.example.musicbrainz.reader.MockArtistProvider.Companion.EXPECTED_NUM_ARTISTS_WHEN_ALL_IDS_VALID
import com.example.musicbrainz.reader.MockArtistProvider.Companion.EXPECTED_NUM_ARTISTS_WHEN_NO_DATA
import com.example.musicbrainz.reader.MockArtistProvider.Companion.EXPECTED_NUM_ARTISTS_WHEN_SOME_EMPTY
import com.example.musicbrainz.reader.MockArtistProvider.Companion.EXPECTED_NUM_ARTISTS_WHEN_SOME_IDS_INVALID
import com.example.musicbrainz.util.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RemoteArtistExtTest : BaseUnitTest() {

    private val verificator = ArtistVerificator()

    @Test
    fun responseHasAllItemsValid_then_conversionToItemsIsCorrect() {
        // given
        val response = artistProvider.getMockArtistsFeedAllIdsValid()

        // when
        val items = response.toItems()

        // then
        verifyListsHaveSameSize(response.artists!!, items)
        verifyListSizeWhenAllIdsValid(response.artists!!, EXPECTED_NUM_ARTISTS_WHEN_ALL_IDS_VALID)
        verifyListSizeWhenAllIdsValid(items, EXPECTED_NUM_ARTISTS_WHEN_ALL_IDS_VALID)
        verificator.verifyItemsAgainstResponse(items, response)
    }

    @Test
    fun responseHasSomeIdsAbsent_then_conversionToItemsIsCorrect() {
        // given
        val response = artistProvider.getMockArtistsFeedSomeIdsAbsent()

        // when
        val items = response.toItems()

        // then
        verifyListSizeWhenSomeIdsAbsent(items, EXPECTED_NUM_ARTISTS_WHEN_SOME_IDS_INVALID)
        verificator.verifyItemsAgainstResponse(items, response)
    }

    @Test
    fun responseHasSomeEmptyItems_then_conversionToItemsIsCorrect() {
        // given
        val response = artistProvider.getMockArtistsFeedSomeItemsEmpty()

        // when
        val items = response.toItems()

        // then
        verifyListSizeWhenSomeItemsEmpty(items, EXPECTED_NUM_ARTISTS_WHEN_SOME_EMPTY)
        verificator.verifyItemsAgainstResponse(items, response)
    }

    @Test
    fun responseHasAllIdsAbsent_then_itemListIsEmpty() {
        // given
        val response = artistProvider.getMockArtistsFeedAllIdsAbsent()

        // when
        val items = response.toItems()

        // then
        verifyListSizeForNoData(items, EXPECTED_NUM_ARTISTS_WHEN_NO_DATA)
    }

    @Test
    fun responseIsAnEmptyJson_then_itemListIsEmpty() {
        // given
        val response = artistProvider.getMockArtistsFeedEmptyJson()

        // when
        val items = response.toItems()

        // then
        verifyListSizeForNoData(items, EXPECTED_NUM_ARTISTS_WHEN_NO_DATA)
    }

}
