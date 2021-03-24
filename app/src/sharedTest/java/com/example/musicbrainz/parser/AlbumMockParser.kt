package com.example.musicbrainz.parser

import com.example.musicbrainz.domain.Album
import com.example.musicbrainz.framework.datasource.network.model.album.AlbumFeed
import com.example.musicbrainz.framework.extensions.toItems

class AlbumMockParser(private val parser: BaseFileParser) {

    companion object {

        const val EXPECTED_NUM_ALBUMS_WHEN_ALL_IDS_VALID = 10
        const val EXPECTED_NUM_ALBUMS_WHEN_SOME_IDS_INVALID = 4
        const val EXPECTED_NUM_ALBUMS_WHEN_SOME_EMPTY = 5
        const val EXPECTED_NUM_ALBUMS_WHEN_NO_DATA = 0

        private const val TEST_ALBUMS_FILE_10_VALID_ITEMS =
            "test-data/albums/10_albums_all_valid.json"
        private const val TEST_ALBUMS_FILE_10_ITEMS_6_IDS_INVALID =
            "test-data/albums/10_albums_6_ids_invalid.json"
        private const val TEST_ALBUMS_FILE_10_ITEMS_5_EMPTY =
            "test-data/albums/10_albums_5_items_empty.json"
        private const val TEST_ALBUMS_FILE_10_ITEMS_ALL_IDS_INVALID =
            "test-data/albums/10_albums_all_ids_invalid.json"
        private const val TEST_FILE_EMPTY_JSON = "test-data/empty_json.json"
    }

    // album items

    fun getMockAlbumsFromFeedWithAllItemsValid(): List<Album> =
        getMockAlbumsFeedAllIdsValid().toItems()

    fun getMockAlbumsFromFeedWithSomeIdsInvalid(): List<Album> =
        getMockAlbumsFeedSomeIdsInvalid().toItems()

    fun getMockAlbumsFromFeedWithSomeItemsEmpty(): List<Album> =
        getMockAlbumsFeedSomeItemsEmpty().toItems()

    fun getMockAlbumsFromFeedWithAllIdsInvalid(): List<Album> =
        getMockAlbumsFeedAllIdsInvalid().toItems()

    fun getMockAlbumsFromFeedWithEmptyJson(): List<Album> =
        getMockAlbumsFeedEmptyJson().toItems()

    // json object model

    fun getMockAlbumsFeedAllIdsValid(): AlbumFeed =
        getMockAlbumsFeed(getMockAlbumsDataAllIdsValid())

    fun getMockAlbumsFeedSomeIdsInvalid(): AlbumFeed =
        getMockAlbumsFeed(getMockAlbumsDataSomeIdsInvalid())

    fun getMockAlbumsFeedSomeItemsEmpty(): AlbumFeed =
        getMockAlbumsFeed(getMockAlbumsDataSomeItemsEmpty())

    fun getMockAlbumsFeedAllIdsInvalid(): AlbumFeed =
        getMockAlbumsFeed(getMockAlbumsDataAllIdsInvalid())

    fun getMockAlbumsFeedEmptyJson(): AlbumFeed =
        getMockAlbumsFeed(mockAlbumsDataEmptyJson())

    private fun getMockAlbumsFeed(text: String): AlbumFeed {
        return convertToModel(text)
    }

    // raw string json

    private fun getMockAlbumsDataAllIdsValid(): String =
        parser.getFileAsString(TEST_ALBUMS_FILE_10_VALID_ITEMS)

    private fun getMockAlbumsDataSomeIdsInvalid(): String =
        parser.getFileAsString(TEST_ALBUMS_FILE_10_ITEMS_6_IDS_INVALID)

    private fun getMockAlbumsDataSomeItemsEmpty(): String =
        parser.getFileAsString(TEST_ALBUMS_FILE_10_ITEMS_5_EMPTY)

    private fun getMockAlbumsDataAllIdsInvalid(): String =
        parser.getFileAsString(TEST_ALBUMS_FILE_10_ITEMS_ALL_IDS_INVALID)

    private fun mockAlbumsDataEmptyJson(): String =
        parser.getFileAsString(TEST_FILE_EMPTY_JSON)

}