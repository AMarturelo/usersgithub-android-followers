package com.amarturelo.usersgithub.followers.demo.di.module

import android.content.Context
import com.amarturelo.usersgithub.followers.demo.App
import com.amarturelo.usersgithub.followers.di.module.FollowersModule
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class,
        ViewModelModule::class,
        FollowersModule::class
    ]
)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun provideContext(application: App): Context
}