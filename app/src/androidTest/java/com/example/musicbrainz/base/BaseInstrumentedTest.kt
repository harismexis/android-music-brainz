package com.example.musicbrainz.base

import com.example.musicbrainz.parser.AlbumMockParser
import com.example.musicbrainz.parser.ArtistMockParser
import com.example.musicbrainz.util.InstrumentedFileParser

open class BaseInstrumentedTest {

    protected val mockParser = InstrumentedFileParser()
    protected val artistParser = ArtistMockParser(mockParser)
    protected val albumParser = AlbumMockParser(mockParser)

}