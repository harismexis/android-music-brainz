package com.example.musicbrainz.base

import com.example.musicbrainz.reader.MockAlbumProvider
import com.example.musicbrainz.reader.MockArtistProvider
import com.example.musicbrainz.util.InstrumentedFileReader

open class BaseInstrumentedTest {

    protected val mockParser = InstrumentedFileReader()
    protected val artistParser = MockArtistProvider(mockParser)
    protected val albumParser = MockAlbumProvider(mockParser)

}