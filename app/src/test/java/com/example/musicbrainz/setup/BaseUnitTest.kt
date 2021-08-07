package com.example.musicbrainz.setup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.musicbrainz.base.BaseTest
import com.example.musicbrainz.reader.BaseFileReader
import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Rule

abstract class BaseUnitTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun doBefore() {
        MockKAnnotations.init(this)
        initialiseClassUnderTest()
    }

    abstract fun initialiseClassUnderTest()

    override fun getBaseFileReader(): BaseFileReader = UnitTestFileReader()

}