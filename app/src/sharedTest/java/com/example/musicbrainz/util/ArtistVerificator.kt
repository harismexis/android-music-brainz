package com.example.musicbrainz.util

import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.framework.data.model.artist.ArtistFeed
import com.example.musicbrainz.framework.datasource.network.model.artist.RemoteArtist
import com.example.musicbrainz.parser.ArtistMockParser.Companion.EXPECTED_NUM_ARTISTS_WHEN_NO_DATA
import org.junit.Assert

class ArtistVerificator {

    fun verifyItemsAgainstRemoteFeed(
        actualItems: List<Artist>,
        remoteFeed: ArtistFeed?
    ) {
        if (remoteFeed == null || remoteFeed.artists.isNullOrEmpty()) {
            verifyListSizeForNoData(actualItems, EXPECTED_NUM_ARTISTS_WHEN_NO_DATA)
            return
        }
        val remoteItems = remoteFeed.artists!!
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
        actual: Artist,
        expected: RemoteArtist
    ) {
        Assert.assertEquals(expected.id, actual.id)
        Assert.assertEquals(expected.type, actual.type)
        Assert.assertEquals(expected.score, actual.score)
        Assert.assertEquals(expected.name, actual.name)
        Assert.assertEquals(expected.country, actual.country)
        Assert.assertEquals(expected.area?.name, actual.area)
        Assert.assertEquals(expected.beginArea?.name, actual.beginArea)
        Assert.assertEquals(expected.endArea?.name, actual.endArea)
        Assert.assertEquals(expected.lifeSpan?.begin, actual.beginDate)
        Assert.assertEquals(expected.lifeSpan?.end, actual.endDate)
        Assert.assertEquals(expected.tags?.map { it.name }, actual.tags)
    }
}

