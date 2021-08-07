package com.example.musicbrainz.framework.di.module

import com.example.musicbrainz.data.MusicBrainzBaseRemoteDataSource
import com.example.musicbrainz.data.MusicBrainzRemoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMusicBrainzRemoteRepository(
        dataSource: MusicBrainzBaseRemoteDataSource
    ): MusicBrainzRemoteRepository {
        return MusicBrainzRemoteRepository(dataSource)
    }

}