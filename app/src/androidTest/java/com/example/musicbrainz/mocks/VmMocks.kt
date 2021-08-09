package com.example.musicbrainz.mocks

import com.example.musicbrainz.presentation.screens.detail.viewmodel.DetailVm
import com.example.musicbrainz.presentation.screens.home.viewmodel.HomeVm
import io.mockk.mockk

val mockHomeVm: HomeVm = mockk(relaxed = true)

val mockDetailVm: DetailVm = mockk(relaxed = true)
