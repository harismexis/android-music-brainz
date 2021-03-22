package com.example.musicbrainz.parser

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

abstract class BaseFileParser {

    private inline fun <reified T> convertToFeed(jsonString: String?): T {
        val gson = GsonBuilder().setLenient().create()
        val json: JsonObject = gson.fromJson(jsonString, JsonObject::class.java)
        return Gson().fromJson(json, T::class.java)
    }

    abstract fun getFileAsString(filePath: String): String

}