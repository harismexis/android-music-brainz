package com.example.musicbrainz.base

import com.example.musicbrainz.reader.BaseFileReader
import com.example.musicbrainz.util.InstrumentedFileReader

abstract class BaseInstrumentedTest: BaseTest() {

    override fun getBaseFileReader(): BaseFileReader = InstrumentedFileReader()
}