package com.example.musicbrainz.reader

abstract class BaseFileReader {

    abstract fun getFileAsString(filePath: String): String
}