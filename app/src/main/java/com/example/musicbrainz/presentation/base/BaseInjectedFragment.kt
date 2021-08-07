package com.example.musicbrainz.presentation.base

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseInjectedFragment : BaseFragment() {

    @Inject
    protected lateinit var vmFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

}