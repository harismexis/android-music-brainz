package com.example.musicbrainz.parser

abstract class BaseFileParser {

    abstract fun getFileAsString(filePath: String): String

}