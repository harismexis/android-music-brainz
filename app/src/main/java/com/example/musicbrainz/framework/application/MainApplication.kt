package com.example.musicbrainz.framework.application

import com.example.musicbrainz.BuildConfig
import com.example.musicbrainz.framework.di.component.DaggerMainComponent
import com.example.musicbrainz.framework.di.component.MainComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject


class MainApplication : DaggerApplication(), HasAndroidInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    private lateinit var component: MainComponent
    lateinit var userAgent: String

    override fun onCreate() {
        super.onCreate()
        userAgent = buildUserAgent()
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return injector
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        component = DaggerMainComponent.factory().create(this)
        component.inject(this)
        return component
    }

    private fun buildUserAgent(): String {
        val userAgentContact = BuildConfig.USER_AGENT_CONTACT_INFO
        val appName = getAppName()
        val appVersion = getAppVersion()
        return "$appName/$appVersion ( $userAgentContact )"
    }

    private fun getAppVersion(): String {
        var version = "1.0"
        try {
            version = packageManager.getPackageInfo(packageName, 0).versionName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return version
    }

    private fun getAppName(): String {
        var name = "MusicBrainz"
        try {
            name = packageManager.getApplicationLabel(applicationInfo).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return name
    }

}