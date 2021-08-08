package com.example.musicbrainz.util

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.google.android.material.R

fun verifySnackBar(msg: String) {
    Espresso.onView(ViewMatchers.withId(R.id.snackbar_text))
        .check(ViewAssertions.matches(ViewMatchers.withText(msg)))
}