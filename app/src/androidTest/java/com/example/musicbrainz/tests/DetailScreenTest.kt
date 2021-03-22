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
import com.example.musicbrainz.presentation.result.AlbumsResult
import com.example.musicbrainz.presentation.result.ArtistsResult
import com.example.musicbrainz.presentation.screens.activity.MainActivity
import com.example.musicbrainz.setup.base.InstrumentedTestSetup
import com.example.musicbrainz.setup.testutil.withRecycler
import com.example.musicbrainz.setup.viewmodel.MockSharedViewModel
import io.mockk.every
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

    private val mockViewModel = MockSharedViewModel.mockViewModel
    private val mockArtists = artistParser.getMockArtistsFromFeedWithAllItemsValid()
    private val artistsSuccess = ArtistsResult.ArtistsSuccess(mockArtists)

    private var mockAlbums = albumParser.getMockAlbumsFromFeedWithAllItemsValid()
    private val albumsSuccess = AlbumsResult.AlbumsSuccess(mockAlbums)

    @Before
    fun doBeforeTest() {
        every { mockViewModel.fetchAlbums() } returns Unit
    }

    @Test
    fun artistsFeedHasAllItemsValid_then_homeListShowsExpectedItems() {
        // given
        every { mockViewModel.artistsResult } returns MockSharedViewModel.artistsResult
        every { mockViewModel.albumsResult } returns MockSharedViewModel.albumsResult

        // when
        launchActivityAndTriggerSearchResult()
        clickRecyclerAt(0) // click on first item to open Details Screen
        testRule.activity.runOnUiThread {
            MockSharedViewModel.mAlbumsResult.value = albumsSuccess
        }

        // then
        onView(withId(R.id.album_list)).check(matches(ViewMatchers.isDisplayed()))
    }

    private fun launchActivityAndTriggerSearchResult() {
        testRule.launchActivity(null)
        testRule.activity.runOnUiThread {
            MockSharedViewModel.mArtistsResult.value = artistsSuccess
        }
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