package com.example.musicbrainz.framework.di.module

import android.content.Context
import com.example.musicbrainz.framework.application.MainApplication
import com.example.musicbrainz.framework.util.resource.ResourceProvider
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun provideAppContext(app: MainApplication): Context {
        return app.applicationContext
    }

    @Provides
    fun provideResourceProvider(app: MainApplication): ResourceProvider {
        return ResourceProvider(app.applicationContext)
    }

}