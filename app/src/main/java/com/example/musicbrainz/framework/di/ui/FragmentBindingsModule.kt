package com.example.musicbrainz.framework.di.ui

import com.example.musicbrainz.framework.base.BaseFragment
import com.example.musicbrainz.presentation.screens.detail.fragment.DetailFragment
import com.example.musicbrainz.presentation.screens.home.fragment.HomeFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingsModule {

    @ContributesAndroidInjector
    abstract fun baseFragment(): BaseFragment

    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun detailFragment(): DetailFragment
}
