package com.example.musicbrainz.presentation.base

import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseInjectedFragment : BaseDIFragment() {

    @Inject
    protected lateinit var vmFactory: ViewModelProvider.Factory

}