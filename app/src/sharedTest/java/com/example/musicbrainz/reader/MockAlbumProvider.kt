package com.example.musicbrainz.reader

import com.example.musicbrainz.domain.Album
import com.example.musicbrainz.framework.data.model.album.AlbumsResponse
import com.example.musicbrainz.framework.util.extensions.toItems
import com.example.musicbrainz.util.convertToModel

class MockAlbumProvider(private val reader: BaseFileReader) {

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

    fun getMockAlbumsFeedAllIdsValid(): AlbumsResponse =
        getMockAlbumsFeed(getMockAlbumsDataAllIdsValid())

    fun getMockAlbumsFeedSomeIdsInvalid(): AlbumsResponse =
        getMockAlbumsFeed(getMockAlbumsDataSomeIdsInvalid())

    fun getMockAlbumsFeedSomeItemsEmpty(): AlbumsResponse =
        getMockAlbumsFeed(getMockAlbumsDataSomeItemsEmpty())

    fun getMockAlbumsFeedAllIdsInvalid(): AlbumsResponse =
        getMockAlbumsFeed(getMockAlbumsDataAllIdsInvalid())

    fun getMockAlbumsFeedEmptyJson(): AlbumsResponse =
        getMockAlbumsFeed(mockAlbumsDataEmptyJson())

    private fun getMockAlbumsFeed(text: String): AlbumsResponse {
        return convertToModel(text)
    }

    // raw string json

    private fun getMockAlbumsDataAllIdsValid(): String =
        reader.getFileAsString(TEST_ALBUMS_FILE_10_VALID_ITEMS)

    private fun getMockAlbumsDataSomeIdsInvalid(): String =
        reader.getFileAsString(TEST_ALBUMS_FILE_10_ITEMS_6_IDS_INVALID)

    private fun getMockAlbumsDataSomeItemsEmpty(): String =
        reader.getFileAsString(TEST_ALBUMS_FILE_10_ITEMS_5_EMPTY)

    private fun getMockAlbumsDataAllIdsInvalid(): String =
        reader.getFileAsString(TEST_ALBUMS_FILE_10_ITEMS_ALL_IDS_INVALID)

    private fun mockAlbumsDataEmptyJson(): String =
        reader.getFileAsString(TEST_FILE_EMPTY_JSON)

}