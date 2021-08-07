package com.example.musicbrainz.tests

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.musicbrainz.R
import com.example.musicbrainz.base.BaseInstrumentedTest
import com.example.musicbrainz.config.vmfactory.mockDetailViewModel
import com.example.musicbrainz.config.vmfactory.mockHomeViewModel
import com.example.musicbrainz.domain.Album
import com.example.musicbrainz.framework.util.extensions.tagString
import com.example.musicbrainz.reader.MockAlbumProvider.Companion.EXPECTED_NUM_ALBUMS_WHEN_ALL_IDS_VALID
import com.example.musicbrainz.reader.MockAlbumProvider.Companion.EXPECTED_NUM_ALBUMS_WHEN_NO_DATA
import com.example.musicbrainz.reader.MockAlbumProvider.Companion.EXPECTED_NUM_ALBUMS_WHEN_SOME_EMPTY
import com.example.musicbrainz.reader.MockAlbumProvider.Companion.EXPECTED_NUM_ALBUMS_WHEN_SOME_IDS_INVALID
import com.example.musicbrainz.presentation.screens.activity.MainActivity
import com.example.musicbrainz.presentation.screens.detail.adapter.DetailAdapter
import com.example.musicbrainz.util.*
import com.example.musicbrainz.util.result.AlbumsResult
import com.example.musicbrainz.util.result.ArtistsResult
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailScreenTest : BaseInstrumentedTest() {

    @get:Rule
    val testRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, false)

    private var mockArtists = artistProvider.getMockArtistsFromFeedWithAllItemsValid()
    private var artistsSuccess = ArtistsResult.Success(mockArtists)
    private var clickIndexOnSearchList = 0
    private var mockArtistsResult = MutableLiveData<ArtistsResult>()

    private var mockAlbumsResult = MutableLiveData<AlbumsResult>()
    private lateinit var mockAlbums: List<Album>
    private lateinit var albumsSuccess: AlbumsResult.Success
    private val numOfDetailHeaders = DetailAdapter.Companion.NUM_OF_HEADERS

    init {
        every { mockHomeViewModel.artists } returns mockArtistsResult
        every { mockDetailViewModel.fetchAlbums() } just runs
        every { mockDetailViewModel.hasSelectedArtist() } returns true
        every { mockDetailViewModel.selectedArtist } returns mockArtists[clickIndexOnSearchList]
    }

    @Test
    fun clickSearchResult_opensDetailsAndShowsExpectedDataWhenAlbumFeedHasAllItemsValid() {
        // given
        mockAlbums = albumProvider.getMockAlbumsFromFeedWithAllItemsValid()
        mockAlbumsResultSuccess()

        // when
        openSearchAndClickFirstItemAndLoadAlbums()

        // then
        verifyDetailRecycler(EXPECTED_NUM_ALBUMS_WHEN_ALL_IDS_VALID)
    }

    @Test
    fun clickSearchResult_opensDetailsAndShowsExpectedDataWhenAlbumFeedHasSomeInvalidIds() {
        // given
        mockAlbums = albumProvider.getMockAlbumsFromFeedWithSomeIdsInvalid()
        mockAlbumsResultSuccess()

        // when
        openSearchAndClickFirstItemAndLoadAlbums()

        // then
        verifyDetailRecycler(EXPECTED_NUM_ALBUMS_WHEN_SOME_IDS_INVALID)
    }

    @Test
    fun clickSearchResult_opensDetailsAndShowsExpectedDataWhenAlbumFeedHasSomeEmptyItems() {
        // given
        mockAlbums = albumProvider.getMockAlbumsFromFeedWithSomeItemsEmpty()
        mockAlbumsResultSuccess()

        // when
        openSearchAndClickFirstItemAndLoadAlbums()

        // then
        verifyDetailRecycler(EXPECTED_NUM_ALBUMS_WHEN_SOME_EMPTY)
    }

    @Test
    fun clickSearchResult_opensDetailsAndShowsExpectedDataWhenAlbumFeedHasAllIdsInvalid() {
        // given
        mockAlbums = albumProvider.getMockAlbumsFromFeedWithAllIdsInvalid()
        mockAlbumsResultSuccess()

        // when
        openSearchAndClickFirstItemAndLoadAlbums()

        // then
        verifyDetailRecycler(EXPECTED_NUM_ALBUMS_WHEN_NO_DATA)
    }

    @Test
    fun clickSearchResult_opensDetailsAndShowsExpectedDataWhenAlbumFeedIsEmptyJson() {
        // given
        mockAlbums = albumProvider.getMockAlbumsFromFeedWithEmptyJson()
        mockAlbumsResultSuccess()

        // when
        openSearchAndClickFirstItemAndLoadAlbums()

        // then
        verifyDetailRecycler(EXPECTED_NUM_ALBUMS_WHEN_NO_DATA)
    }

    private fun mockAlbumsResultSuccess() {
        albumsSuccess = AlbumsResult.Success(mockAlbums)
        every { mockDetailViewModel.albums } returns mockAlbumsResult
    }

    private fun openSearchAndClickFirstItemAndLoadAlbums() {
        launchActivityAndTriggerSearchResult()
        clickRecyclerAt(clickIndexOnSearchList) // click an artist on search list to open Details
        triggerAlbumsResult()
    }

    private fun launchActivityAndTriggerSearchResult() {
        testRule.launchActivity(null)
        testRule.activity.runOnUiThread {
            mockArtistsResult.value = artistsSuccess
        }
    }

    private fun triggerAlbumsResult() {
        testRule.activity.runOnUiThread {
            mockAlbumsResult.value = albumsSuccess
        }
    }

    private fun verifyDetailRecycler(expectedNumberOfAlbums: Int) {
        onView(withId(R.id.detail_list)).check(matches(ViewMatchers.isDisplayed()))
        verifyDetailRecyclerCount(expectedNumberOfAlbums)
        verifyDetailRecyclerData()
    }

    private fun verifyDetailRecyclerCount(expectedNumberOfAlbums: Int) {
        Assert.assertEquals(albumsSuccess.items.size, expectedNumberOfAlbums)
        val expectedCount = albumsSuccess.items.size + numOfDetailHeaders
        onView(withId(R.id.detail_list)).check(RecyclerCountAssertion(expectedCount))
    }

    private fun verifyDetailRecyclerData() {
        verifyArtistHeaderRow()
        verifyAlbumsTitleHeaderRow()
        verifyAlbumRows()
    }

    private fun verifyArtistHeaderRow() {
        val index = 0 // artist header is first item
        val artist = mockDetailViewModel.selectedArtist

        onView(withId(R.id.detail_list)).perform(scrollToPosition<RecyclerView.ViewHolder>(index))

        verifyRecyclerValue(index, R.id.txt_name, artist.name)

        verifyRecyclerLabel(index, R.id.txt_type_label, R.string.artist_type_label)
        verifyRecyclerValue(index, R.id.txt_type, artist.type)

        verifyRecyclerLabel(index, R.id.txt_country_label, R.string.artist_country_label)
        verifyRecyclerValue(index, R.id.txt_country, artist.country)

        verifyRecyclerLabel(index, R.id.txt_score_label, R.string.artist_score_label)
        verifyRecyclerValue(index, R.id.txt_score, artist.score.toString())

        verifyRecyclerLabel(index, R.id.txt_area_label, R.string.artist_area_label)
        verifyRecyclerValue(index, R.id.txt_area, artist.area)

        verifyRecyclerLabel(index, R.id.txt_begin_date_label, R.string.artist_begin_date_label)
        verifyRecyclerValue(index, R.id.txt_begin_date, artist.beginDate)

        if (!artist.endDate.isNullOrBlank()) {
            verifyRecyclerLabel(index, R.id.txt_end_date_label, R.string.artist_end_date_label)
            verifyRecyclerValue(index, R.id.txt_end_date, artist.endDate)
        } else {
            verifyLabelAndValueGone(index, R.id.txt_end_date_label, R.id.txt_end_date)
        }

        if (!artist.tagString().isNullOrBlank()) {
            verifyRecyclerLabel(index, R.id.txt_tags_label, R.string.artist_tags_label)
            verifyRecyclerValue(index, R.id.txt_tags, artist.tagString())
        } else {
            verifyLabelAndValueGone(index, R.id.txt_tags_label, R.id.txt_tags)
        }

    }

    private fun verifyAlbumsTitleHeaderRow() {
        val index = 1 // albums title is second item
        onView(withId(R.id.detail_list)).perform(scrollToPosition<RecyclerView.ViewHolder>(index))
        verifyRecyclerLabel(index, R.id.txt_albums_title_header, R.string.albums_title_header)
    }

    private fun verifyAlbumRows() {
        mockAlbums.forEachIndexed { indice, album ->
            val index = indice + numOfDetailHeaders

            onView(withId(R.id.detail_list)).perform(scrollToPosition<RecyclerView.ViewHolder>(index))

            verifyRecyclerValue(index, R.id.txt_title, album.title)

            verifyRecyclerLabel(index, R.id.txt_status_label, R.string.album_status_label)
            verifyRecyclerValue(index, R.id.txt_status, album.status)

            verifyRecyclerLabel(index, R.id.txt_trackcount_label, R.string.album_trackcount_label)
            verifyRecyclerValue(index, R.id.txt_trackcount, album.trackCount.toString())

            verifyRecyclerLabel(index, R.id.txt_date_label, R.string.album_date_label)
            verifyRecyclerValue(index, R.id.txt_date, album.date)

            verifyRecyclerLabel(index, R.id.txt_country_label, R.string.album_country_label)
            verifyRecyclerValue(index, R.id.txt_country, album.country)

            verifyRecyclerLabel(index, R.id.txt_disambiguation_label, R.string.album_disambiguation_label)
            verifyRecyclerValue(index, R.id.txt_disambiguation, album.disambiguation)

            verifyRecyclerLabel(index, R.id.txt_score_label, R.string.album_score_label)
            verifyRecyclerValue(index, R.id.txt_score, album.score.toString())

            verifyRecyclerLabel(index, R.id.txt_packaging_label, R.string.album_packaging_label)
            verifyRecyclerValue(index, R.id.txt_packaging, album.packaging)

            verifyRecyclerLabel(index, R.id.txt_barcode_label, R.string.album_barcode_label)
            verifyRecyclerValue(index, R.id.txt_barcode, album.barcode)
        }
    }

    private fun verifyLabelAndValueGone(index: Int, @IdRes labelId: Int, @IdRes txtId: Int) {
        verifyRecyclerItemGone(index, labelId)
        verifyRecyclerItemGone(index, txtId)
    }

    private fun verifyRecyclerItemGone(index: Int, @IdRes viewId: Int) {
        verifyRecyclerItemGoneAt(R.id.detail_list, index, viewId)
    }

    private fun verifyRecyclerLabel(index: Int, @IdRes viewId: Int, @StringRes resId: Int) {
        verifyRecyclerItemAt(R.id.detail_list, index, viewId, getStringRes(resId))
    }

    private fun verifyRecyclerValue(index: Int, @IdRes viewId: Int, value: String?) {
        verifyRecyclerItemAt(R.id.detail_list, index, viewId, getExpectedText(value))
    }

    private fun clickRecyclerAt(position: Int) {
        onView(withId(R.id.artist_list)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                click()
            )
        )
    }

}