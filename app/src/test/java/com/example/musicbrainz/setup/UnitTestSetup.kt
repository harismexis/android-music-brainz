package com.example.musicbrainz.setup

import com.example.musicbrainz.base.BaseTestSetup
import io.mockk.MockKAnnotations

abstract class UnitTestSetup : BaseTestSetup() {

    protected val fileParser = UnitTestFileParser()

    open fun initialise() {
        MockKAnnotations.init(this)
        initialiseClassUnderTest()
    }

    abstract fun initialiseClassUnderTest()

}