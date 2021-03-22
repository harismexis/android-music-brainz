package com.example.musicbrainz.parser

import com.example.musicbrainz.domain.Album
import com.example.musicbrainz.framework.datasource.network.model.album.AlbumFeed
import com.example.musicbrainz.framework.extensions.toItems

class AlbumMockParser(private val parser: BaseFileParser) {

    companion object {

        const val EXPECTED_NUM_ALBUMS_WHEN_ALL_IDS_VALID = 5
        const val EXPECTED_NUM_ALBUMS_WHEN_TWO_IDS_ABSENT = 3
        const val EXPECTED_NUM_ALBUMS_WHEN_TWO_EMPTY = 3
        const val EXPECTED_NUM_ALBUMS_WHEN_NO_DATA = 0

        private const val TEST_ALBUMS_FILE_FIVE_VALID_ITEMS =
            "albums/remote_five_albums_all_valid.json"
        private const val TEST_ALBUMS_FILE_FIVE_ITEMS_BUT_TWO_IDS_ABSENT =
            "albums/remote_five_albums_two_ids_absent.json"
        private const val TEST_ALBUMS_FILE_FIVE_ITEMS_BUT_TWO_EMPTY =
            "albums/remote_five_albums_two_items_empty.json"
        private const val TEST_ALBUMS_FILE_FIVE_ITEMS_ALL_IDS_ABSENT =
            "albums/remote_five_albums_all_ids_absent.json"
        private const val TEST_FILE_EMPTY_JSON = "empty_json.json"
    }

    // get artist items

    fun getMockAlbum(): Album = getMockAlbumsFromFeedWithAllItemsValid()[0]

    fun getMockAlbumsFromFeedWithAllItemsValid(): List<Album> =
        getMockAlbumsFeedAllIdsValid().toItems()

    fun getMockAlbumsFromFeedWithSomeIdsAbsent(): List<Album> =
        getMockAlbumsFeedSomeIdsAbsent().toItems()

    fun getMockAlbumsFromFeedWithSomeItemsEmpty(): List<Album> =
        getMockAlbumsFeedSomeItemsEmpty().toItems()

    fun getMockAlbumsFromFeedWithAllIdsAbsent(): List<Album> =
        getMockAlbumsFeedAllIdsAbsent().toItems()

    fun getMockAlbumsFromFeedWithEmptyJson(): List<Album> =
        getMockAlbumsFeedEmptyJson().toItems()

    // get json object model

    fun getMockAlbumsFeedAllIdsValid(): AlbumFeed =
        getMockAlbumsFeed(getMockAlbumsDataAllIdsValid())

    fun getMockAlbumsFeedSomeIdsAbsent(): AlbumFeed =
        getMockAlbumsFeed(getMockAlbumsDataSomeIdsAbsent())

    fun getMockAlbumsFeedSomeItemsEmpty(): AlbumFeed =
        getMockAlbumsFeed(getMockAlbumsDataSomeItemsEmpty())

    fun getMockAlbumsFeedAllIdsAbsent(): AlbumFeed =
        getMockAlbumsFeed(getMockAlbumsDataAllIdsAbsent())

    fun getMockAlbumsFeedEmptyJson(): AlbumFeed =
        getMockAlbumsFeed(mockAlbumsDataEmptyJson())

    private fun getMockAlbumsFeed(text: String): AlbumFeed {
        return convertToModel(text)
    }

    // get raw string json

    private fun getMockAlbumsDataAllIdsValid(): String =
        parser.getFileAsString(TEST_ALBUMS_FILE_FIVE_VALID_ITEMS)

    private fun getMockAlbumsDataSomeIdsAbsent(): String =
        parser.getFileAsString(TEST_ALBUMS_FILE_FIVE_ITEMS_BUT_TWO_IDS_ABSENT)

    private fun getMockAlbumsDataSomeItemsEmpty(): String =
        parser.getFileAsString(TEST_ALBUMS_FILE_FIVE_ITEMS_BUT_TWO_EMPTY)

    private fun getMockAlbumsDataAllIdsAbsent(): String =
        parser.getFileAsString(TEST_ALBUMS_FILE_FIVE_ITEMS_ALL_IDS_ABSENT)

    private fun mockAlbumsDataEmptyJson(): String =
        parser.getFileAsString(TEST_FILE_EMPTY_JSON)

}