package com.amarturelo.usersgithub.followers.data.datasource

import com.amarturelo.usersgithub.core.commons.utils.Either
import com.amarturelo.usersgithub.core.exception.Failure
import com.amarturelo.usersgithub.followers.data.model.FollowerModel


interface FollowerDataSource {
    suspend fun followersByUsername(username: String): Either<Failure, List<FollowerModel>>
}