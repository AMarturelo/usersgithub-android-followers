package com.amarturelo.usersgithub.followers.domain.usecase

import com.amarturelo.usersgithub.core.commons.utils.Either
import com.amarturelo.usersgithub.core.domain.usecase.UseCase
import com.amarturelo.usersgithub.core.exception.Failure
import com.amarturelo.usersgithub.followers.domain.entity.FollowerEntity
import com.amarturelo.usersgithub.followers.domain.repository.FollowerRepository
import javax.inject.Inject

class GetFollowersByUsernameUseCase @Inject constructor(private val repository: FollowerRepository) :
    UseCase<Either<Failure, List<FollowerEntity>>, GetFollowersByUsernameUseCaseParams>() {
    override suspend fun run(params: GetFollowersByUsernameUseCaseParams): Either<Failure, List<FollowerEntity>> {
        return repository.followersByUsername(params.username)
    }
}

data class GetFollowersByUsernameUseCaseParams(val username: String)