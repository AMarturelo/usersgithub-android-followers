package com.amarturelo.usersgithub.followers.demo.di.module

import android.content.Context
import com.amarturelo.usersgithub.followers.demo.App
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(
    includes = [
        ViewModelModule::class,
        NetworkModule::class,
        //FollowersModule::class
    ]
)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun provideContext(application: App): Context
}