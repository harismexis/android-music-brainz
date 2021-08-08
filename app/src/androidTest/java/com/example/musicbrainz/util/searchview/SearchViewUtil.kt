package com.example.musicbrainz.util.searchview

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers

fun submitSearchQuery(
    @IdRes searchViewId: Int,
    text: String) {
    Espresso.onView(ViewMatchers.withId(searchViewId)).perform(
        SearchViewActions
            .submitQuery(text)
    )
}