package com.example.musicbrainz.setup.application

import com.example.musicbrainz.setup.di.component.DaggerInstrumentedComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class InstrumentedApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val mainComponent = DaggerInstrumentedComponent.factory().create(this)
        mainComponent.inject(this)
        return mainComponent
    }

}