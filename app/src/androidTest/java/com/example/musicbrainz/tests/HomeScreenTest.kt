package com.example.musicbrainz.tests

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.musicbrainz.R
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.parser.ArtistMockParser.Companion.EXPECTED_NUM_ARTISTS_WHEN_ALL_IDS_VALID
import com.example.musicbrainz.parser.ArtistMockParser.Companion.EXPECTED_NUM_ARTISTS_WHEN_NO_DATA
import com.example.musicbrainz.parser.ArtistMockParser.Companion.EXPECTED_NUM_ARTISTS_WHEN_TWO_EMPTY
import com.example.musicbrainz.parser.ArtistMockParser.Companion.EXPECTED_NUM_ARTISTS_WHEN_TWO_IDS_ABSENT
import com.example.musicbrainz.presentation.result.ArtistsResult
import com.example.musicbrainz.presentation.screens.activity.MainActivity
import com.example.musicbrainz.presentation.viewmodel.SharedViewModel
import com.example.musicbrainz.setup.base.InstrumentedTestSetup
import com.example.musicbrainz.setup.testutil.RecyclerCountAssertion
import com.example.musicbrainz.setup.testutil.RecyclerViewMatcher
import com.example.musicbrainz.setup.testutil.getExpectedText
import com.example.musicbrainz.setup.testutil.getStringRes
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
    fun artistsFeedHasAllItemsValid_then_listShowsExpectedItems() {
        // given
        every { mockViewModel.artistsResult } returns MockSharedViewModel.artistsResult

        // when
        launchActivityAndTriggerSearchResult()

        // then
        verifyRecycler(EXPECTED_NUM_ARTISTS_WHEN_ALL_IDS_VALID)
    }

    @Test
    fun remoteFeedHasSomeInvalidIds_listShowsExpectedItems() {
        // given
        mockArtists = artistParser.getMockArtistsFromFeedWithSomeIdsAbsent()
        artistsSuccess = ArtistsResult.ArtistsSuccess(mockArtists)
        every { mockViewModel.artistsResult } returns MockSharedViewModel.artistsResult

        // when
        launchActivityAndTriggerSearchResult()

        // then
        verifyRecycler(EXPECTED_NUM_ARTISTS_WHEN_TWO_IDS_ABSENT)
    }

    @Test
    fun remoteFeedHasSomeEmptyArtistJsonItems_listHasExpectedNumberOfItems() {
        // given
        mockArtists = artistParser.getMockArtistsFromFeedWithSomeItemsEmpty()
        artistsSuccess = ArtistsResult.ArtistsSuccess(mockArtists)
        every { mockViewModel.artistsResult } returns MockSharedViewModel.artistsResult

        // when
        launchActivityAndTriggerSearchResult()

        // then
        verifyRecycler(EXPECTED_NUM_ARTISTS_WHEN_TWO_EMPTY)
    }

    @Test
    fun remoteFeedHasAllIdsInvalid_listShowsNoItems() {
        // given
        mockArtists = artistParser.getMockArtistsFromFeedWithAllIdsAbsent()
        artistsSuccess = ArtistsResult.ArtistsSuccess(mockArtists)
        every { mockViewModel.artistsResult } returns MockSharedViewModel.artistsResult

        // when
        launchActivityAndTriggerSearchResult()

        // then
        verifyRecycler(EXPECTED_NUM_ARTISTS_WHEN_NO_DATA)
    }

    @Test
    fun remoteFeedIsEmptyJson_listShowsNoItems() {
        // given
        mockArtists = artistParser.getMockArtistsFromFeedWithEmptyJson()
        artistsSuccess = ArtistsResult.ArtistsSuccess(mockArtists)
        every { mockViewModel.artistsResult } returns MockSharedViewModel.artistsResult

        // when
        launchActivityAndTriggerSearchResult()

        // then
        verifyRecycler(EXPECTED_NUM_ARTISTS_WHEN_NO_DATA)
    }

    private fun launchActivityAndTriggerSearchResult() {
        testRule.launchActivity(null)
        testRule.activity.runOnUiThread {
            MockSharedViewModel.mArtistsResult.value = artistsSuccess
        }
    }

    private fun verifyRecycler(expectedNumberOfItems: Int) {
        onView(withId(R.id.artist_list)).check(matches(isDisplayed()))
        verifyRecyclerCount(expectedNumberOfItems)
        verifyRecyclerData()
    }

    private fun verifyRecyclerCount(expectedNumberOfItems: Int) {
        onView(withId(R.id.artist_list)).check(RecyclerCountAssertion(artistsSuccess.items.size))
        onView(withId(R.id.artist_list)).check(RecyclerCountAssertion(expectedNumberOfItems))
    }

    private fun verifyRecyclerData() {
        mockArtists.forEachIndexed { index, artist ->
            onView(withId(R.id.artist_list)).perform(scrollToPosition<RecyclerView.ViewHolder>(index))

            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_name))
                .check(matches(withText(artist.name)))

            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_type_label))
                .check(matches(withText(getStringRes(R.string.vh_artist_type_label))))
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_type))
                .check(matches(withText(getExpectedText(artist.type))))

            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_country_label))
                .check(matches(withText(getStringRes(R.string.vh_artist_country_label))))
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_country))
                .check(matches(withText(getExpectedText(artist.country))))

            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_score_label))
                .check(matches(withText(getStringRes(R.string.vh_artist_score_label))))
            onView(withRecycler(R.id.artist_list).atPositionOnView(index, R.id.txt_score))
                .check(matches(withText(getExpectedText(artist.score.toString()))))
        }
    }

    private fun withRecycler(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }


}