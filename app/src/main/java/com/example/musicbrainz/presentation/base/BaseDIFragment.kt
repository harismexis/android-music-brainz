package com.example.musicbrainz.presentation.base

import android.content.Context
import dagger.android.support.AndroidSupportInjection

abstract class BaseDIFragment : BaseFragment() {

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

}