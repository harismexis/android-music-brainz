package com.example.musicbrainz.base

import com.example.musicbrainz.reader.BaseFileReader
import com.example.musicbrainz.reader.MockAlbumProvider
import com.example.musicbrainz.reader.MockArtistProvider

abstract class BaseTest {

    abstract fun getBaseFileReader(): BaseFileReader

    protected val artistProvider by lazy { MockArtistProvider(getBaseFileReader()) }
    protected val albumProvider by lazy { MockAlbumProvider(getBaseFileReader()) }
}