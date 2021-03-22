package com.example.musicbrainz.tests

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.musicbrainz.R
import com.example.musicbrainz.parser.AlbumMockParser.Companion.EXPECTED_NUM_ALBUMS_WHEN_ALL_IDS_VALID
import com.example.musicbrainz.parser.AlbumMockParser.Companion.EXPECTED_NUM_ALBUMS_WHEN_NO_DATA
import com.example.musicbrainz.parser.AlbumMockParser.Companion.EXPECTED_NUM_ALBUMS_WHEN_TWO_EMPTY
import com.example.musicbrainz.parser.AlbumMockParser.Companion.EXPECTED_NUM_ALBUMS_WHEN_TWO_IDS_ABSENT
import com.example.musicbrainz.presentation.result.AlbumsResult
import com.example.musicbrainz.presentation.result.ArtistsResult
import com.example.musicbrainz.presentation.screens.activity.MainActivity
import com.example.musicbrainz.setup.base.InstrumentedTestSetup
import com.example.musicbrainz.setup.testutil.RecyclerCountAssertion
import com.example.musicbrainz.setup.testutil.withRecycler
import com.example.musicbrainz.setup.viewmodel.MockSharedViewModelProvider
import io.mockk.every
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DetailScreenTest : InstrumentedTestSetup() {

    @get:Rule
    val testRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(
            MainActivity::class.java,
            false, false
        )

    private val mockViewModel = MockSharedViewModelProvider.mockSharedViewModel
    private val mockArtists = artistParser.getMockArtistsFromFeedWithAllItemsValid()
    private val artistsSuccess = ArtistsResult.ArtistsSuccess(mockArtists)

    private var mockAlbums = albumParser.getMockAlbumsFromFeedWithAllItemsValid()
    private var albumsSuccess = AlbumsResult.AlbumsSuccess(mockAlbums)

    @Before
    fun doBeforeEachTest() {
        every { mockViewModel.artistsResult } returns MockSharedViewModelProvider.artistsResult
        every { mockViewModel.fetchAlbums() } returns Unit
        every { mockViewModel.hasSelectedArtist() } returns true
    }

    @Test
    fun clickSearchResult_opensDetailsAndShowsExpectedDataWhenAlbumFeedHasAllItemsValid() {
        // given
        every { mockViewModel.albumsResult } returns MockSharedViewModelProvider.albumsResult

        // when
        openSearchAndClickFirstItemAndLoadAlbums()

        // then
        verifyRecycler(EXPECTED_NUM_ALBUMS_WHEN_ALL_IDS_VALID)
    }

    @Test
    fun clickSearchResult_opensDetailsAndShowsExpectedDataWhenAlbumFeedHasSomeIdsInvalid() {
        // given
        mockAlbums = albumParser.getMockAlbumsFromFeedWithSomeIdsAbsent()
        albumsSuccess = AlbumsResult.AlbumsSuccess(mockAlbums)
        every { mockViewModel.albumsResult } returns MockSharedViewModelProvider.albumsResult

        // when
        openSearchAndClickFirstItemAndLoadAlbums()

        // then
        verifyRecycler(EXPECTED_NUM_ALBUMS_WHEN_TWO_IDS_ABSENT)
    }

    @Test
    fun clickSearchResult_opensDetailsAndShowsExpectedDataWhenAlbumFeedHasSomeItemsEmpty() {
        // given
        mockAlbums = albumParser.getMockAlbumsFromFeedWithSomeItemsEmpty()
        albumsSuccess = AlbumsResult.AlbumsSuccess(mockAlbums)
        every { mockViewModel.albumsResult } returns MockSharedViewModelProvider.albumsResult

        // when
        openSearchAndClickFirstItemAndLoadAlbums()

        // then
        verifyRecycler(EXPECTED_NUM_ALBUMS_WHEN_TWO_EMPTY)
    }

    @Test
    fun clickSearchResult_opensDetailsAndShowsExpectedDataWhenAlbumFeedHasAllIdsInvalid() {
        // given
        mockAlbums = albumParser.getMockAlbumsFromFeedWithAllIdsAbsent()
        albumsSuccess = AlbumsResult.AlbumsSuccess(mockAlbums)
        every { mockViewModel.albumsResult } returns MockSharedViewModelProvider.albumsResult

        // when
        openSearchAndClickFirstItemAndLoadAlbums()

        // then
        verifyRecycler(EXPECTED_NUM_ALBUMS_WHEN_NO_DATA)
    }

    @Test
    fun clickSearchResult_opensDetailsAndShowsExpectedDataWhenAlbumFeedIsEmptyJson() {
        // given
        mockAlbums = albumParser.getMockAlbumsFromFeedWithEmptyJson()
        albumsSuccess = AlbumsResult.AlbumsSuccess(mockAlbums)
        every { mockViewModel.albumsResult } returns MockSharedViewModelProvider.albumsResult

        // when
        openSearchAndClickFirstItemAndLoadAlbums()

        // then
        verifyRecycler(EXPECTED_NUM_ALBUMS_WHEN_NO_DATA)
    }

    private fun openSearchAndClickFirstItemAndLoadAlbums() {
        launchActivityAndTriggerSearchResult()
        clickRecyclerAt(0) // click on first item to open Details Screen
        mockViewModel.selectedArtist = mockArtists[0]
        triggerAlbumsResult()
    }

    private fun launchActivityAndTriggerSearchResult() {
        testRule.launchActivity(null)
        testRule.activity.runOnUiThread {
            MockSharedViewModelProvider.mArtistsResult.value = artistsSuccess
        }
    }

    private fun triggerAlbumsResult() {
        testRule.activity.runOnUiThread {
            MockSharedViewModelProvider.mAlbumsResult.value = albumsSuccess
        }
    }

    private fun verifyRecycler(expectedNumberOfAlbums: Int) {
        onView(withId(R.id.album_list)).check(matches(ViewMatchers.isDisplayed()))
        verifyRecyclerCount(expectedNumberOfAlbums)
        //verifyRecyclerData()
    }

    private fun verifyRecyclerCount(expectedNumberOfAlbums: Int) {
        Assert.assertEquals(albumsSuccess.items.size, expectedNumberOfAlbums)
        // we add 2 items cause detail list has a header and title as first 2 items
        val expectedCount = albumsSuccess.items.size + 2
        onView(withId(R.id.album_list)).check(RecyclerCountAssertion(expectedCount))
    }

    private fun clickRecyclerAt(position: Int) {
        onView(withId(R.id.artist_list)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                click()
            )
        )
    }

    private fun verifyRecyclerViewShowsExpectedData() {
        mockArtists.forEachIndexed { index, artist ->
            // scroll to item to make sure it's visible
            onView(withId(R.id.artist_list)).perform(scrollToPosition<RecyclerView.ViewHolder>(index))

            // name
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_name))
                .check(matches(withText(artist.name)))

            // type
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_type_label))
                .check(matches(withText(getString(R.string.vh_artist_type_label))))
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_type))
                .check(matches(withText(artist.type)))

            // country
            onView(
                withRecycler(R.id.artist_list).atPositionOnView(
                    index,
                    R.id.txt_country_label
                )
            )
                .check(matches(withText(getString(R.string.vh_artist_country_label))))
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_country))
                .check(matches(withText(artist.country)))

            // score
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_score_label))
                .check(matches(withText(getString(R.string.vh_artist_score_label))))
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_score))
                .check(matches(withText(artist.score.toString())))
        }
    }

    private fun verifyHeaderShowsExpectedData() {
        mockArtists.forEachIndexed { index, artist ->
            // scroll to item to make sure it's visible
            onView(withId(R.id.artist_list)).perform(scrollToPosition<RecyclerView.ViewHolder>(index))

            // name
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_name))
                .check(matches(withText(artist.name)))

            // type
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_type_label))
                .check(matches(withText(getString(R.string.vh_artist_type_label))))
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_type))
                .check(matches(withText(artist.type)))

            // country
            onView(
                withRecycler(R.id.artist_list).atPositionOnView(
                    index,
                    R.id.txt_country_label
                )
            )
                .check(matches(withText(getString(R.string.vh_artist_country_label))))
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_country))
                .check(matches(withText(artist.country)))

            // score
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_score_label))
                .check(matches(withText(getString(R.string.vh_artist_score_label))))
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_score))
                .check(matches(withText(artist.score.toString())))
        }
    }

    private fun verifyAlbumRows() {
        mockArtists.forEachIndexed { index, artist ->
            // scroll to item to make sure it's visible
            onView(withId(R.id.artist_list)).perform(scrollToPosition<RecyclerView.ViewHolder>(index))

            // name
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_name))
                .check(matches(withText(artist.name)))

            // type
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_type_label))
                .check(matches(withText(getString(R.string.vh_artist_type_label))))
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_type))
                .check(matches(withText(artist.type)))

            // country
            onView(
                withRecycler(R.id.artist_list).atPositionOnView(
                    index,
                    R.id.txt_country_label
                )
            )
                .check(matches(withText(getString(R.string.vh_artist_country_label))))
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_country))
                .check(matches(withText(artist.country)))

            // score
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_score_label))
                .check(matches(withText(getString(R.string.vh_artist_score_label))))
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_score))
                .check(matches(withText(artist.score.toString())))
        }
    }

    private fun getString(id: Int): String {
        return InstrumentationRegistry.getInstrumentation()
            .targetContext.resources.getString(id)
    }

}