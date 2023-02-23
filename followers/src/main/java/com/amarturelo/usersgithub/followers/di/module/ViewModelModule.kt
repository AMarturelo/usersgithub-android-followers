package com.amarturelo.usersgithub.followers.di.module

import androidx.lifecycle.ViewModel
import com.amarturelo.usersgithub.core.di.ViewModelKey
import com.amarturelo.usersgithub.followers.presentation.followers.FollowersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FollowersViewModel::class)
    abstract fun bindFollowersViewModel(viewModel: FollowersViewModel): ViewModel
}