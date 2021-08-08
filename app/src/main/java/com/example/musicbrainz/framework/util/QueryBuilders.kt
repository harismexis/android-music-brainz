package com.example.musicbrainz.framework.util

import org.apache.lucene.queryparser.classic.QueryParser

fun formatArtistsQuery(artistName: String): String {
    return QueryParser.escape(artistName)
}

fun formatAlbumsQuery(artistId: String): String {
    return "arid:$artistId"
}