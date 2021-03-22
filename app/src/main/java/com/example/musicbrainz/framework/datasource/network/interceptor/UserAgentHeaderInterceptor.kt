package com.example.musicbrainz.framework.datasource.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class UserAgentHeaderInterceptor(
    private val apiKey: String
) : Interceptor {

    companion object {
        const val USER_AGENT_HEADER = "User-Agent"
    }

    override fun intercept(chain: Interceptor.Chain): Response =
        chain.run {
            proceed(
                request()
                    .newBuilder()
                    .addHeader(USER_AGENT_HEADER, apiKey)
                    .build()
            )
        }
}