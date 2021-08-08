package com.example.musicbrainz.tests

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.musicbrainz.R
import com.example.musicbrainz.base.BaseInstrumentedTest
import com.example.musicbrainz.config.vmfactory.mockHomeVm
import com.example.musicbrainz.domain.Artist
import com.example.musicbrainz.presentation.screens.activity.MainActivity
import com.example.musicbrainz.util.SearchViewActionExtension
import com.example.musicbrainz.util.event.Event
import com.example.musicbrainz.util.getExpectedText
import com.example.musicbrainz.util.getStringRes
import com.example.musicbrainz.util.recycler.RecyclerCountAssertion
import com.example.musicbrainz.util.recycler.verifyRecyclerItemAt
import com.example.musicbrainz.util.result.ArtistsResult
import com.example.musicbrainz.util.verifySnackBar
import io.mockk.every
import org.hamcrest.CoreMatchers
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest : BaseInstrumentedTest() {

    private val searchResult = MutableLiveData<ArtistsResult>()
    private val showMsg = MutableLiveData<Event<String>>()

    private lateinit var mockArtists: List<Artist>

    private fun startActivity(): ActivityScenario<MainActivity> {
        return launchActivity()
    }

    @Test
    fun artistsResponseHasAllItemsValid_then_listShowsExpectedItems() {
        // given
        mockSearchResults(artistProvider.getMockArtistsFromFeedWithAllItemsValid())

        // when
        startActivity()
        performSearch("metallica")

        // then
        verifyRecycler()
    }

    @Test
    fun artistsResponseHasSomeInvalidIds_listShowsExpectedItems() {
        // given
        mockSearchResults(artistProvider.getMockArtistsFromFeedWithSomeIdsInvalid())

        // when
        startActivity()
        performSearch("metallica")

        // then
        verifyRecycler()
    }

    @Test
    fun artistsResponseHasSomeEmptyItems_listHasExpectedNumberOfItems() {
        // given
        mockSearchResults(artistProvider.getMockArtistsFromFeedWithSomeItemsEmpty())

        // when
        startActivity()
        performSearch("metallica")

        // then
        verifyRecycler()
    }

    @Test
    fun artistsResponseHasAllIdsInvalid_listShowsNoItems() {
        // given
        mockSearchResults(artistProvider.getMockArtistsFromFeedWithAllIdsInvalid())

        // when
        startActivity()
        performSearch("metallica")

        // then
        verifyRecycler()
    }

    @Test
    fun artistsResponseIsEmptyJson_listShowsNoItems() {
        // given
        mockSearchResults(artistProvider.getMockArtistsFromFeedWithEmptyJson())

        // when
        startActivity()
        performSearch("metallica")

        // then
        verifyRecycler()
    }

    @Test
    fun searchThrowsError_snackBarShown() {
        // given
        val errorMsg = "Sorry, failed to retrieve results"
        mockSearchError(errorMsg)

        // when
        startActivity()
        performSearch("metallica")

        // then
        verifySnackBar(errorMsg)
    }

    private fun mockSearchResults(
        mockData: List<Artist>
    ) {
        mockArtists = mockData
        every { mockHomeVm.search(any()) } answers {
            searchResult.value = ArtistsResult.Success(mockArtists)
        }
        every { mockHomeVm.artists } returns searchResult
    }

    private fun mockSearchError(error: String) {
        every { mockHomeVm.search(any()) } answers {
            searchResult.value = ArtistsResult.Error(Exception(error))
            showMsg.value = Event(error)
        }
        every { mockHomeVm.artists } returns searchResult
        every { mockHomeVm.showMsgEvent } returns showMsg
    }

    private fun performSearch(text: String) {
        onView(withId(R.id.searchView)).perform(
            SearchViewActionExtension
                .submitQuery(text)
        )
    }

    private fun verifyRecycler() {
        val hasItems = mockArtists.isNotEmpty()
        verifyNoResultsVisible(!hasItems)
        verifyRecyclerVisible(hasItems)
        verifyRecyclerData(mockArtists.size)
    }

    private fun verifyRecyclerVisible(
        visible: Boolean
    ) {
        verifyViewVisible(R.id.artist_list, visible)
    }

    private fun verifyNoResultsVisible(
        visible: Boolean
    ) {
        verifyViewVisible(R.id.no_results, visible)
    }

    private fun verifyViewVisible(
        @IdRes id: Int,
        visible: Boolean
    ) {
        if (visible)
            onView(withId(id)).check(matches(isDisplayed()))
        else
            onView(withId(id)).check(matches(CoreMatchers.not(isDisplayed())))
    }

    private fun verifyRecyclerData(
        expectedNumberOfItems: Int
    ) {
        verifyRecyclerCount(expectedNumberOfItems)
        verifyRecyclerRows()
    }

    private fun verifyRecyclerCount(expectedNumberOfItems: Int) {
        // Checking also if the mock result success has expected number of items
        // TODO:
        // Assert.assertEquals(artistsResult.items.size, expectedNumberOfItems)
        // Checking if recycler has expected number of items
        onView(withId(R.id.artist_list)).check(RecyclerCountAssertion(expectedNumberOfItems))
    }

    private fun verifyRecyclerRows() {
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