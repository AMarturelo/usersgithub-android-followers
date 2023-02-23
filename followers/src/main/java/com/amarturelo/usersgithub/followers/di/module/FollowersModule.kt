package com.amarturelo.usersgithub.followers.di.module

import com.amarturelo.usersgithub.followers.data.api.FollowersAPI
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module
class FollowersModule {
    @Provides
    @Reusable
    fun provideFollowersApi(retrofit: Retrofit): FollowersAPI {
        return retrofit.create(FollowersAPI::class.java)
    }
}