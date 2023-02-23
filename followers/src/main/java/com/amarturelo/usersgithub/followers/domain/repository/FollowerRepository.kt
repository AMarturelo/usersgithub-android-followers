package com.amarturelo.usersgithub.followers.domain.repository

import com.amarturelo.usersgithub.core.commons.utils.Either
import com.amarturelo.usersgithub.core.exception.Failure
import com.amarturelo.usersgithub.followers.domain.entity.FollowerEntity

interface FollowerRepository {
    suspend fun followersByUsername(username: String): Either<Failure, List<FollowerEntity>>
}