package com.example.musicbrainz.framework.di.module

import com.example.musicbrainz.BuildConfig
import com.example.musicbrainz.framework.application.MainApplication
import com.example.musicbrainz.framework.data.api.MusicBrainzApi
import com.example.musicbrainz.framework.data.interceptor.UserAgentHeaderInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class MusicBrainzApiModule {

    @Singleton
    @Provides
    fun provideMusicBrainzApi(retrofit: Retrofit): MusicBrainzApi {
        return retrofit.create(MusicBrainzApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.MUSICBRAINZ_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(app: MainApplication): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(UserAgentHeaderInterceptor(app.userAgent))
            .build()
    }

    @Singleton
    @Provides
    fun provideGSON(): Gson {
        return GsonBuilder().setLenient().create()
    }

}