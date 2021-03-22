package com.example.musicbrainz.setup.base

import com.example.musicbrainz.parser.AlbumMockParser
import com.example.musicbrainz.parser.ArtistMockParser
import com.example.musicbrainz.setup.testutil.InstrumentedFileParser

open class InstrumentedTestSetup {

    protected val mockParser = InstrumentedFileParser()
    protected val artistParser = ArtistMockParser(mockParser)
    protected val albumParser = AlbumMockParser(mockParser)

}