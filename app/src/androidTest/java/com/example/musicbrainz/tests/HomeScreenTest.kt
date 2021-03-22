package com.example.musicbrainz.tests

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.musicbrainz.R
import com.example.musicbrainz.parser.ArtistMockParser.Companion.EXPECTED_NUM_ARTISTS_WHEN_ALL_IDS_VALID
import com.example.musicbrainz.parser.ArtistMockParser.Companion.EXPECTED_NUM_ARTISTS_WHEN_NO_DATA
import com.example.musicbrainz.parser.ArtistMockParser.Companion.EXPECTED_NUM_ARTISTS_WHEN_TWO_EMPTY
import com.example.musicbrainz.parser.ArtistMockParser.Companion.EXPECTED_NUM_ARTISTS_WHEN_TWO_IDS_ABSENT
import com.example.musicbrainz.presentation.result.ArtistsResult
import com.example.musicbrainz.presentation.screens.activity.MainActivity
import com.example.musicbrainz.setup.base.InstrumentedTestSetup
import com.example.musicbrainz.setup.testutil.RecyclerCountAssertion
import com.example.musicbrainz.setup.testutil.getExpectedText
import com.example.musicbrainz.setup.testutil.getStringRes
import com.example.musicbrainz.setup.testutil.withRecycler
import com.example.musicbrainz.setup.viewmodel.MockSharedViewModelProvider
import io.mockk.every
import org.junit.Assert
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

    private val mockViewModel = MockSharedViewModelProvider.mockSharedViewModel
    private var mockArtists = artistParser.getMockArtistsFromFeedWithAllItemsValid()
    private var artistsSuccess = ArtistsResult.ArtistsSuccess(mockArtists)

    @Test
    fun artistsFeedHasAllItemsValid_then_listShowsExpectedItems() {
        // given
        every { mockViewModel.artistsResult } returns MockSharedViewModelProvider.artistsResult

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
        every { mockViewModel.artistsResult } returns MockSharedViewModelProvider.artistsResult

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
        every { mockViewModel.artistsResult } returns MockSharedViewModelProvider.artistsResult

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
        every { mockViewModel.artistsResult } returns MockSharedViewModelProvider.artistsResult

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
        every { mockViewModel.artistsResult } returns MockSharedViewModelProvider.artistsResult

        // when
        launchActivityAndTriggerSearchResult()

        // then
        verifyRecycler(EXPECTED_NUM_ARTISTS_WHEN_NO_DATA)
    }

    private fun launchActivityAndTriggerSearchResult() {
        testRule.launchActivity(null)
        testRule.activity.runOnUiThread {
            MockSharedViewModelProvider.mArtistsResult.value = artistsSuccess
        }
    }

    private fun verifyRecycler(expectedNumberOfItems: Int) {
        onView(withId(R.id.artist_list)).check(matches(isDisplayed()))
        verifyRecyclerCount(expectedNumberOfItems)
        verifyRecyclerData()
    }

    private fun verifyRecyclerCount(expectedNumberOfItems: Int) {
        // Checking if the mock result success has correct number of items
        Assert.assertEquals(artistsSuccess.items.size, expectedNumberOfItems)
        // Checking if recycler has correct number of items
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

}