package com.example.musicbrainz.setup

import com.example.musicbrainz.parser.BaseFileParser

class UnitTestFileParser : BaseFileParser() {

    override fun getFileAsString(filePath: String): String =
        ClassLoader.getSystemResource(filePath).readText()

}

