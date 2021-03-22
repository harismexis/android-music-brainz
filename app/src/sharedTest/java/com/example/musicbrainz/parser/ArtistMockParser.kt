package com.example.musicbrainz.parser

import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.framework.datasource.network.model.artist.ArtistFeed
import com.example.musicbrainz.framework.extensions.toItems

class ArtistMockParser(private val parser: BaseFileParser) {

    companion object {

        const val EXPECTED_NUM_ARTISTS_WHEN_ALL_IDS_VALID = 10
        const val EXPECTED_NUM_ARTISTS_WHEN_SOME_IDS_INVALID = 6
        const val EXPECTED_NUM_ARTISTS_WHEN_SOME_EMPTY = 7
        const val EXPECTED_NUM_ARTISTS_WHEN_NO_DATA = 0

        private const val TEST_ARTISTS_FILE_10_VALID_ITEMS =
            "test-data/artists/10_artists_all_valid.json"
        private const val TEST_ARTISTS_FILE_10_ITEMS_4_IDS_INVALID =
            "test-data/artists/10_artists_4_ids_invalid.json"
        private const val TEST_ARTISTS_FILE_10_ITEMS_3_EMPTY =
            "test-data/artists/10_artists_3_items_empty.json"
        private const val TEST_ARTISTS_FILE_10_ITEMS_ALL_IDS_INVALID =
            "test-data/artists/10_artists_all_ids_invalid.json"
        private const val TEST_ARTISTS_FILE_EMPTY_JSON = "test-data/empty_json.json"
    }

    // get artist items

    fun getMockArtist(): Artist = getMockArtistsFromFeedWithAllItemsValid()[0]

    fun getMockArtistsFromFeedWithAllItemsValid(): List<Artist> =
        getMockArtistsFeedAllIdsValid().toItems()

    fun getMockArtistsFromFeedWithSomeIdsInvalid(): List<Artist> =
        getMockArtistsFeedSomeIdsAbsent().toItems()

    fun getMockArtistsFromFeedWithSomeItemsEmpty(): List<Artist> =
        getMockArtistsFeedSomeItemsEmpty().toItems()

    fun getMockArtistsFromFeedWithAllIdsInvalid(): List<Artist> =
        getMockArtistsFeedAllIdsAbsent().toItems()

    fun getMockArtistsFromFeedWithEmptyJson(): List<Artist> =
        getMockArtistsFeedEmptyJson().toItems()

    // get json object model

    fun getMockArtistsFeedAllIdsValid(): ArtistFeed =
        getMockArtistsFeed(getMockArtistsDataAllIdsValid())

    fun getMockArtistsFeedSomeIdsAbsent(): ArtistFeed =
        getMockArtistsFeed(getMockArtistsDataSomeIdsAbsent())

    fun getMockArtistsFeedSomeItemsEmpty(): ArtistFeed =
        getMockArtistsFeed(getMockArtistsDataSomeItemsEmpty())

    fun getMockArtistsFeedAllIdsAbsent(): ArtistFeed =
        getMockArtistsFeed(getMockArtistsDataAllIdsAbsent())

    fun getMockArtistsFeedEmptyJson(): ArtistFeed =
        getMockArtistsFeed(mockArtistsDataEmptyJson())

    private fun getMockArtistsFeed(text: String): ArtistFeed {
        return convertToModel(text)
    }

    // get raw string json

    private fun getMockArtistsDataAllIdsValid(): String =
        parser.getFileAsString(TEST_ARTISTS_FILE_10_VALID_ITEMS)

    private fun getMockArtistsDataSomeIdsAbsent(): String =
        parser.getFileAsString(TEST_ARTISTS_FILE_10_ITEMS_4_IDS_INVALID)

    private fun getMockArtistsDataSomeItemsEmpty(): String =
        parser.getFileAsString(TEST_ARTISTS_FILE_10_ITEMS_3_EMPTY)

    private fun getMockArtistsDataAllIdsAbsent(): String =
        parser.getFileAsString(TEST_ARTISTS_FILE_10_ITEMS_ALL_IDS_INVALID)

    private fun mockArtistsDataEmptyJson(): String =
        parser.getFileAsString(TEST_ARTISTS_FILE_EMPTY_JSON)

}