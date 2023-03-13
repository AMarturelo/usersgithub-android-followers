package com.amarturelo.usersgithub.followers.data.api

import com.amarturelo.usersgithub.followers.commons.Constants.API.FOLLOWERS
import com.amarturelo.usersgithub.followers.commons.Constants.API.PATH_USERNAME
import com.amarturelo.usersgithub.followers.data.model.FollowerModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FollowerAPI {
    @GET(FOLLOWERS)
    suspend fun followersByUsername(@Path(PATH_USERNAME) username: String): Response<List<FollowerModel>>
}