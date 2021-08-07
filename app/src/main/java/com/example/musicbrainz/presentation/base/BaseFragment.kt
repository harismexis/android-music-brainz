package com.example.musicbrainz.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initialiseViewBinding(inflater, container)
        initialiseView()
        return getRootView()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated()
    }

    abstract fun initialiseViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )

    abstract fun getRootView(): View?

    abstract fun onViewCreated()

    abstract fun observeLiveData()

    abstract fun initialiseView()

}