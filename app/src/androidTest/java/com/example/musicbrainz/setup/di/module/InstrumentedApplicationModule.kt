package com.example.musicbrainz.setup.di.module

import android.content.Context

import com.example.musicbrainz.setup.application.InstrumentedMainApplication

import dagger.Module
import dagger.Provides

@Module
class InstrumentedApplicationModule {

    @Provides
    fun providesContext(app: InstrumentedMainApplication): Context {
        return app.applicationContext
    }

}