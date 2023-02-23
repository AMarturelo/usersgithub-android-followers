package com.amarturelo.usersgithub.followers.demo.di.module

import com.amarturelo.usersgithub.core.commons.Constants.API.API_CONNECTION_TIMEOUT
import com.amarturelo.usersgithub.core.commons.Constants.API.ISO_DATE_FORMAT
import com.amarturelo.usersgithub.followers.demo.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
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

    @Reusable
    @Provides
    fun providesGson(): Gson {
        val builder = GsonBuilder()
        builder.setDateFormat(ISO_DATE_FORMAT)
        builder.setLenient()
        return builder.create()
    }

    @Reusable
    @Provides
    fun providesRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        val builder = Retrofit.Builder()
        builder.baseUrl(BuildConfig.HOST)
        builder.client(client)
        builder.addConverterFactory(GsonConverterFactory.create(gson))
        return builder.build()
    }
}