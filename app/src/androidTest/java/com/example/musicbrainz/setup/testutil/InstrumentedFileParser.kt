package com.example.musicbrainz.setup.testutil

import androidx.test.platform.app.InstrumentationRegistry
import com.example.musicbrainz.parser.BaseFileParser

class InstrumentedFileParser: BaseFileParser() {

    override fun getFileAsString(filePath: String): String =
        InstrumentationRegistry.getInstrumentation().context.classLoader
            .getResource(filePath).readText()

}

