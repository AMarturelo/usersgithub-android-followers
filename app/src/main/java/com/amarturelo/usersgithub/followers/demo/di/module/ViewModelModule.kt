package com.amarturelo.usersgithub.followers.demo.di.module

import androidx.lifecycle.ViewModelProvider
import com.amarturelo.usersgithub.core.presentation.ViewModelFactory
import dagger.Binds
import dagger.Module

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}