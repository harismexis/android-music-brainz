package com.example.musicbrainz.util

import com.example.musicbrainz.reader.BaseFileReader

class UnitTestFileReader : BaseFileReader() {

    override fun getFileAsString(filePath: String): String =
        ClassLoader.getSystemResource(filePath).readText()
}

