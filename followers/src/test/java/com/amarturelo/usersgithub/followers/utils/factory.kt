package com.amarturelo.usersgithub.followers.utils

import com.amarturelo.usersgithub.core.commons.Constants.API.API_CONNECTION_TIMEOUT
import com.amarturelo.usersgithub.core.commons.Constants.API.ISO_DATE_FORMAT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


fun providesOkHttpClientBuilder(
): OkHttpClient {
    val builder: OkHttpClient.Builder = OkHttpClient.Builder().apply {
        addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
    }
    builder.readTimeout(API_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
    builder.writeTimeout(API_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
    builder.connectTimeout(API_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
    builder.retryOnConnectionFailure(false)
    return builder.build()
}

fun providesGson(): Gson {
    val builder = GsonBuilder()
    builder.setDateFormat(ISO_DATE_FORMAT)
    builder.setLenient()
    return builder.create()
}