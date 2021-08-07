package com.example.musicbrainz.util

import com.example.musicbrainz.domain.Album
import com.example.musicbrainz.framework.data.model.album.AlbumFeed
import com.example.musicbrainz.framework.data.model.album.RemoteAlbum
import com.example.musicbrainz.reader.MockAlbumProvider.Companion.EXPECTED_NUM_ALBUMS_WHEN_NO_DATA
import org.junit.Assert

class AlbumVerificator {

    fun verifyItemsAgainstRemoteFeed(
        actualItems: List<Album>,
        remoteFeed: AlbumFeed?
    ) {
        if (remoteFeed == null || remoteFeed.releases.isNullOrEmpty()) {
            verifyListSizeForNoData(actualItems, EXPECTED_NUM_ALBUMS_WHEN_NO_DATA)
            return
        }
        val remoteItems = remoteFeed.releases!!
        remoteItems.forEach lit@{ remoteItem ->
            if (remoteItem == null || remoteItem.id.isNullOrBlank()) return@lit
            actualItems.forEach { actualItem ->
                if (remoteItem.id == actualItem.id) {
                    verifyItemAgainstRemoteItem(actualItem, remoteItem)
                    return@lit
                }
            }
        }
    }

    private fun verifyItemAgainstRemoteItem(
        actual: Album,
        expected: RemoteAlbum
    ) {
        Assert.assertEquals(expected.id, actual.id)
        Assert.assertEquals(expected.score, actual.score)
        Assert.assertEquals(expected.title, actual.title)
        Assert.assertEquals(expected.status, actual.status)
        Assert.assertEquals(expected.disambiguation, actual.disambiguation)
        Assert.assertEquals(expected.packaging, actual.packaging)
        Assert.assertEquals(expected.date, actual.date)
        Assert.assertEquals(expected.country, actual.country)
        Assert.assertEquals(expected.barcode, actual.barcode)
        Assert.assertEquals(expected.trackCount, actual.trackCount)
    }

}

