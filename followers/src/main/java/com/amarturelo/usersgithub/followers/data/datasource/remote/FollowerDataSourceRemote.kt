package com.amarturelo.usersgithub.followers.data.datasource.remote

import com.amarturelo.usersgithub.core.commons.utils.Either
import com.amarturelo.usersgithub.core.exception.Failure
import com.amarturelo.usersgithub.followers.data.api.FollowerAPI
import com.amarturelo.usersgithub.followers.data.datasource.FollowerDataSource
import com.amarturelo.usersgithub.followers.data.model.FollowerModel
import javax.inject.Inject

class FollowerDataSourceRemote @Inject constructor(private val api: FollowerAPI) :
    FollowerDataSource {
    override suspend fun followersByUsername(username: String): Either<Failure, List<FollowerModel>> {
        val response = api.followersByUsername(username)
        return when (response.isSuccessful) {
            true -> Either.Right(response.body() ?: listOf())
            false -> Either.Left(Failure.UnknownError)
        }
    }

}