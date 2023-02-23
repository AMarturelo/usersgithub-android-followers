package com.amarturelo.usersgithub.followers.di.module

import dagger.Module
import dagger.Provides
import dagger.Reusable
import com.amarturelo.usersgithub.followers.data.datasource.FollowerDataSource
import com.amarturelo.usersgithub.followers.data.datasource.remote.FollowerDataSourceRemote
import com.amarturelo.usersgithub.followers.data.repository.FollowerRepositoryData
import com.amarturelo.usersgithub.followers.domain.repository.FollowerRepository
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Reusable
    fun provideFollowersDataSourceRemote(dataSource: FollowerDataSourceRemote): FollowerDataSource {
        return dataSource
    }

    @Provides
    @Reusable
    fun provideUserRepository(repository: FollowerRepositoryData): FollowerRepository {
        return repository
    }
}