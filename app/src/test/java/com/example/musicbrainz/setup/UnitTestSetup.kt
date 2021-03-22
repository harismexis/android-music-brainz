package com.example.musicbrainz.setup

import com.example.musicbrainz.base.BaseTestSetup
import org.mockito.MockitoAnnotations

abstract class UnitTestSetup : BaseTestSetup() {

    protected val fileParser = UnitTestFileParser()

    open fun initialise() {
        MockitoAnnotations.initMocks(this)
        initialiseClassUnderTest()
    }

    abstract fun initialiseClassUnderTest()

}