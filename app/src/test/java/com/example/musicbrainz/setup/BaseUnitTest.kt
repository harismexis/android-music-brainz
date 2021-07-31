package com.example.musicbrainz.setup

import com.example.musicbrainz.base.BaseTest
import io.mockk.MockKAnnotations

abstract class BaseUnitTest : BaseTest() {

    protected val fileParser = UnitTestFileParser()

    open fun initialise() {
        MockKAnnotations.init(this)
        initialiseClassUnderTest()
    }

    abstract fun initialiseClassUnderTest()

}