package com.example.musicbrainz.util

import androidx.test.platform.app.InstrumentationRegistry
import com.example.musicbrainz.reader.BaseFileReader

class InstrumentedFileReader: BaseFileReader() {

    override fun getFileAsString(filePath: String): String =
        InstrumentationRegistry.getInstrumentation().context.classLoader
            .getResource(filePath).readText()

}

