package com.amarturelo.usersgithub.followers.di.module

import com.amarturelo.usersgithub.followers.data.api.FollowerAPI
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Reusable
    fun provideFollowersApi(retrofit: Retrofit): FollowerAPI {
        return retrofit.create(FollowerAPI::class.java)
    }
}