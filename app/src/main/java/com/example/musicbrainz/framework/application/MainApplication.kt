package com.example.musicbrainz.framework.application

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.example.musicbrainz.BuildConfig
import com.example.musicbrainz.framework.di.DaggerMainComponent
import com.example.musicbrainz.framework.di.MainComponent
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
        val name = applicationInfo.name
        var version = "1.0"
        try {
            val info: PackageInfo = packageManager.getPackageInfo(packageName, 0)
            version = info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return "$name/$version ( $userAgentContact )"
    }

}