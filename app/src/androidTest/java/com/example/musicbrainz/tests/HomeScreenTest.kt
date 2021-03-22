package com.example.musicbrainz.tests

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.musicbrainz.R
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.presentation.result.ArtistsResult
import com.example.musicbrainz.presentation.screens.activity.MainActivity
import com.example.musicbrainz.presentation.viewmodel.SharedViewModel
import com.example.musicbrainz.setup.base.InstrumentedTestSetup
import com.example.musicbrainz.setup.testutil.RecyclerViewItemCountAssertion
import com.example.musicbrainz.setup.testutil.RecyclerViewMatcher
import com.example.musicbrainz.setup.viewmodel.MockSharedViewModel
import io.mockk.every
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HomeScreenTest : InstrumentedTestSetup() {

    @get:Rule
    val testRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(
            MainActivity::class.java,
            false, false
        )

    private lateinit var mockViewModel: SharedViewModel
    private lateinit var mockArtists: List<Artist>
    private lateinit var artistsSuccess: ArtistsResult.ArtistsSuccess

    @Before
    fun doBeforeTest() {
        mockArtists = artistParser.getMockArtistsFromFeedWithAllItemsValid()
        artistsSuccess = ArtistsResult.ArtistsSuccess(mockArtists)
        mockViewModel = MockSharedViewModel.mockViewModel
    }

    @Test
    fun artistsFeedHasAllItemsValid_then_homeListShowsExpectedItems() {
        // given
        every { mockViewModel.artistsResult } returns MockSharedViewModel.artistsResult

        // when
        launchActivityAndTriggerSearchResult()

        // then
        onView(withId(R.id.artist_list)).check(matches(isDisplayed()))
        onView(withId(R.id.artist_list)).check(RecyclerViewItemCountAssertion(artistsSuccess.items.size))
//        onView(withId(R.id.artist_list)).check(
//            RecyclerViewItemCountAssertion(EXPECTED_NUM_ARTISTS_WHEN_ALL_IDS_VALID)
//        )
        //verifyRecyclerViewShowsExpectedData()
    }

    private fun launchActivityAndTriggerSearchResult() {
        testRule.launchActivity(null)
        testRule.activity.runOnUiThread {
            MockSharedViewModel.mArtistsResult.value = artistsSuccess
        }
    }

    private fun verifyRecyclerViewShowsExpectedData() {
        mockArtists.forEachIndexed { index, artist ->
            // scroll to item to make sure it's visible
            onView(withId(R.id.artist_list)).perform(scrollToPosition<RecyclerView.ViewHolder>(index))

            // name
            onView(withRecyclerView(R.id.artist_list).atPositionOnView(index, R.id.txt_name))
                .check(matches(withText(artist.name)))

            // type
            onView(withRecyclerView(R.id.artist_list).atPositionOnView(index, R.id.txt_type_label))
                .check(matches(withText(getString(R.string.vh_artist_type_label))))
            onView(withRecyclerView(R.id.artist_list).atPositionOnView(index, R.id.txt_type))
                .check(matches(withText(artist.type)))

            // country
            onView(withRecyclerView(R.id.artist_list).atPositionOnView(index, R.id.txt_country_label))
                .check(matches(withText(getString(R.string.vh_artist_country_label))))
            onView(withRecyclerView(R.id.artist_list).atPositionOnView(index, R.id.txt_country))
                .check(matches(withText(artist.country)))

            // score
            onView(withRecyclerView(R.id.artist_list).atPositionOnView(index, R.id.txt_score_label))
                .check(matches(withText(getString(R.string.vh_artist_score_label))))
            onView(withRecyclerView(R.id.artist_list).atPositionOnView(index, R.id.txt_score))
                .check(matches(withText(artist.score.toString())))
        }
    }

    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }

    private fun getString(id: Int): String {
        return InstrumentationRegistry.getInstrumentation()
            .targetContext.resources.getString(id)
    }

}