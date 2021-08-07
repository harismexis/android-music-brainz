package com.example.musicbrainz.base

import com.example.musicbrainz.reader.BaseFileReader
import com.example.musicbrainz.util.InstrumentedFileReader

open class BaseInstrumentedTest: BaseTest() {

    override fun getBaseFileReader(): BaseFileReader = InstrumentedFileReader()

}