package com.example.musicbrainz.tests

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.musicbrainz.R
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.parser.ArtistMockParser.Companion.EXPECTED_NUM_ARTISTS_WHEN_ALL_IDS_VALID
import com.example.musicbrainz.parser.ArtistMockParser.Companion.EXPECTED_NUM_ARTISTS_WHEN_NO_DATA
import com.example.musicbrainz.parser.ArtistMockParser.Companion.EXPECTED_NUM_ARTISTS_WHEN_SOME_EMPTY
import com.example.musicbrainz.parser.ArtistMockParser.Companion.EXPECTED_NUM_ARTISTS_WHEN_SOME_IDS_INVALID
import com.example.musicbrainz.presentation.result.ArtistsResult
import com.example.musicbrainz.presentation.screens.activity.MainActivity
import com.example.musicbrainz.setup.base.InstrumentedSetup
import com.example.musicbrainz.setup.util.RecyclerCountAssertion
import com.example.musicbrainz.setup.util.getExpectedText
import com.example.musicbrainz.setup.util.getStringRes
import com.example.musicbrainz.setup.util.verifyRecyclerItemAt
import com.example.musicbrainz.setup.viewmodel.factory.mockHomeViewModel
import io.mockk.every
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest : InstrumentedSetup() {

    @get:Rule
    val testRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(
            MainActivity::class.java,
            false, false
        )

    private var mockArtistsResult = MutableLiveData<ArtistsResult>()
    private lateinit var mockArtists: List<Artist>
    private lateinit var artistsSuccess: ArtistsResult.Success

    @Test
    fun artistsFeedHasAllItemsValid_then_listShowsExpectedItems() {
        // given
        mockArtists = artistParser.getMockArtistsFromFeedWithAllItemsValid()
        mockSearchResultSuccess()

        // when
        launchActivityAndTriggerSearchResult()

        // then
        verifyRecycler(EXPECTED_NUM_ARTISTS_WHEN_ALL_IDS_VALID)
    }

    @Test
    fun remoteFeedHasSomeInvalidIds_listShowsExpectedItems() {
        // given
        mockArtists = artistParser.getMockArtistsFromFeedWithSomeIdsInvalid()
        mockSearchResultSuccess()

        // when
        launchActivityAndTriggerSearchResult()

        // then
        verifyRecycler(EXPECTED_NUM_ARTISTS_WHEN_SOME_IDS_INVALID)
    }

    @Test
    fun remoteFeedHasSomeEmptyArtistJsonItems_listHasExpectedNumberOfItems() {
        // given
        mockArtists = artistParser.getMockArtistsFromFeedWithSomeItemsEmpty()
        mockSearchResultSuccess()

        // when
        launchActivityAndTriggerSearchResult()

        // then
        verifyRecycler(EXPECTED_NUM_ARTISTS_WHEN_SOME_EMPTY)
    }

    @Test
    fun remoteFeedHasAllIdsInvalid_listShowsNoItems() {
        // given
        mockArtists = artistParser.getMockArtistsFromFeedWithAllIdsInvalid()
        mockSearchResultSuccess()

        // when
        launchActivityAndTriggerSearchResult()

        // then
        verifyRecycler(EXPECTED_NUM_ARTISTS_WHEN_NO_DATA)
    }

    @Test
    fun remoteFeedIsEmptyJson_listShowsNoItems() {
        // given
        mockArtists = artistParser.getMockArtistsFromFeedWithEmptyJson()
        mockSearchResultSuccess()

        // when
        launchActivityAndTriggerSearchResult()

        // then
        verifyRecycler(EXPECTED_NUM_ARTISTS_WHEN_NO_DATA)
    }

    private fun mockSearchResultSuccess() {
        artistsSuccess = ArtistsResult.Success(mockArtists)
        every { mockHomeViewModel.artistsResult } returns mockArtistsResult
    }

    private fun launchActivityAndTriggerSearchResult() {
        testRule.launchActivity(null)
        testRule.activity.runOnUiThread {
            mockArtistsResult.value = artistsSuccess
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

            verifyRecyclerValue(index, R.id.txt_name, artist.name)

            verifyRecyclerLabel(index, R.id.txt_type_label, R.string.artist_type_label)
            verifyRecyclerValue(index, R.id.txt_type, artist.type)

            verifyRecyclerLabel(index, R.id.txt_country_label, R.string.artist_country_label)
            verifyRecyclerValue(index, R.id.txt_country, artist.country)

            verifyRecyclerLabel(index, R.id.txt_score_label, R.string.artist_score_label)
            verifyRecyclerValue(index, R.id.txt_score, artist.score.toString())
        }
    }

    private fun verifyRecyclerLabel(
        index: Int,
        @IdRes viewId: Int,
        @StringRes resId: Int
    ) {
        verifyRecyclerItemAt(R.id.artist_list, index, viewId, getStringRes(resId))
    }

    private fun verifyRecyclerValue(
        index: Int,
        @IdRes viewId: Int,
        value: String?
    ) {
        verifyRecyclerItemAt(R.id.artist_list, index, viewId, getExpectedText(value))
    }

}