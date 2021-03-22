package com.example.musicbrainz.framework.di.application

import android.content.Context
import com.example.musicbrainz.framework.application.MainApplication
import com.example.musicbrainz.framework.resource.ResourceProvider
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun provideAppContext(app: MainApplication): Context {
        return app.applicationContext
    }

    @Provides
    fun provideResourceProvider(context: Context): ResourceProvider {
        return ResourceProvider(context.applicationContext)
    }

}