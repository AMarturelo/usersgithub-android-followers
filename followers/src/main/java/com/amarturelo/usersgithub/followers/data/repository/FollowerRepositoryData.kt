package com.amarturelo.usersgithub.followers.data.repository

import com.amarturelo.usersgithub.core.commons.utils.Either
import com.amarturelo.usersgithub.core.commons.utils.map
import com.amarturelo.usersgithub.core.exception.Failure
import com.amarturelo.usersgithub.followers.data.datasource.FollowerDataSource
import com.amarturelo.usersgithub.followers.data.model.toEntity
import com.amarturelo.usersgithub.followers.domain.entity.FollowerEntity
import com.amarturelo.usersgithub.followers.domain.repository.FollowerRepository
import javax.inject.Inject

class FollowerRepositoryData @Inject constructor(private val remoteDataSource: FollowerDataSource) :
    FollowerRepository {
    override suspend fun followersByUsername(username: String): Either<Failure, List<FollowerEntity>> {
        return remoteDataSource.followersByUsername(username).map { result ->
            result.map { it.toEntity() }
        }
    }
}