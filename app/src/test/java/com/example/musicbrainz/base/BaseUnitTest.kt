package com.example.musicbrainz.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.musicbrainz.reader.BaseFileReader
import com.example.musicbrainz.util.UnitTestFileReader
import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Rule

abstract class BaseUnitTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun doBefore() {
        MockKAnnotations.init(this)
        onDoBefore()
    }

    open fun onDoBefore() {}

    override fun getBaseFileReader(): BaseFileReader = UnitTestFileReader()
}