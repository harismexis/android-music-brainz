package com.example.musicbrainz.framework.di.module

import com.example.musicbrainz.data.MusicBrainzBaseRemoteDataSource
import com.example.musicbrainz.framework.data.api.MusicBrainzApi
import com.example.musicbrainz.framework.data.data.MusicBrainzRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatasourceModule {

    @Singleton
    @Provides
    fun provideMusicBrainzBaseRemoteDataSource(
        api: MusicBrainzApi
    ): MusicBrainzBaseRemoteDataSource {
        return MusicBrainzRemoteDataSource(api)
    }

}