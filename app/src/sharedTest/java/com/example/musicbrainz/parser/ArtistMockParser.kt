package com.example.musicbrainz.parser

import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.framework.datasource.network.model.artist.ArtistFeed
import com.example.musicbrainz.framework.extensions.toItems

class ArtistMockParser(private val parser: BaseFileParser) {

    companion object {

        const val EXPECTED_NUM_ARTISTS_WHEN_ALL_IDS_VALID = 5
        const val EXPECTED_NUM_ARTISTS_WHEN_TWO_IDS_ABSENT = 3
        const val EXPECTED_NUM_ARTISTS_WHEN_TWO_EMPTY = 3
        const val EXPECTED_NUM_ARTISTS_WHEN_NO_DATA = 0

        private const val TEST_ARTISTS_FILE_FIVE_VALID_ITEMS =
            "artists/remote_five_artists_all_valid.json"
        private const val TEST_ARTISTS_FILE_FIVE_ITEMS_BUT_TWO_IDS_ABSENT =
            "artists/remote_five_artists_two_ids_absent.json"
        private const val TEST_ARTISTS_FILE_FIVE_ITEMS_BUT_TWO_EMPTY =
            "artists/remote_five_artists_two_items_empty.json"
        private const val TEST_ARTISTS_FILE_FIVE_ITEMS_ALL_IDS_ABSENT =
            "artists/remote_five_artists_all_ids_absent.json"
        private const val TEST_ARTISTS_FILE_EMPTY_JSON = "empty_json.json"
    }

    // get artist items

    fun getMockArtist(): Artist = getMockArtistsFromFeedWithAllItemsValid()[0]

    fun getMockArtistsFromFeedWithAllItemsValid(): List<Artist> =
        getMockArtistsFeedAllIdsValid().toItems()

    fun getMockArtistsFromFeedWithSomeIdsAbsent(): List<Artist> =
        getMockArtistsFeedSomeIdsAbsent().toItems()

    fun getMockArtistsFromFeedWithSomeItemsEmpty(): List<Artist> =
        getMockArtistsFeedSomeItemsEmpty().toItems()

    fun getMockArtistsFromFeedWithAllIdsAbsent(): List<Artist> =
        getMockArtistsFeedAllIdsAbsent().toItems()

    fun getMockArtistsFromFeedWithEmptyJsonArray(): List<Artist> =
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
        parser.getFileAsString(TEST_ARTISTS_FILE_FIVE_VALID_ITEMS)

    private fun getMockArtistsDataSomeIdsAbsent(): String =
        parser.getFileAsString(TEST_ARTISTS_FILE_FIVE_ITEMS_BUT_TWO_IDS_ABSENT)

    private fun getMockArtistsDataSomeItemsEmpty(): String =
        parser.getFileAsString(TEST_ARTISTS_FILE_FIVE_ITEMS_BUT_TWO_EMPTY)

    private fun getMockArtistsDataAllIdsAbsent(): String =
        parser.getFileAsString(TEST_ARTISTS_FILE_FIVE_ITEMS_ALL_IDS_ABSENT)

    private fun mockArtistsDataEmptyJson(): String =
        parser.getFileAsString(TEST_ARTISTS_FILE_EMPTY_JSON)

}