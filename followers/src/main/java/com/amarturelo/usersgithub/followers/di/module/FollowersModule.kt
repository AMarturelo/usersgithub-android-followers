package com.amarturelo.usersgithub.followers.di.module

import com.amarturelo.usersgithub.followers.presentation.followers.FollowersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [
        ViewModelModule::class,
        DataModule::class,
        NetworkModule::class,
    ]
)
abstract class FollowersModule {

    @ContributesAndroidInjector
    abstract fun contributeFollowersFragment(): FollowersFragment
}