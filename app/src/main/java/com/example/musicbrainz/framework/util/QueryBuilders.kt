package com.example.musicbrainz.framework.util

import org.apache.lucene.queryparser.classic.QueryParser

fun buildSearchQuery(artistName: String): String {
    return QueryParser.escape(artistName)
}

fun buildAlbumsQuery(artistId: String): String {
    return "arid:$artistId"
}