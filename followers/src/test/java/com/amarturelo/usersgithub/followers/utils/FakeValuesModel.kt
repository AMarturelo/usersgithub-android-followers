package com.amarturelo.usersgithub.followers.utils

import com.amarturelo.usersgithub.followers.data.model.FollowerModel


object FakeValuesModel {
    fun user(): FollowerModel {
        return FollowerModel(
            avatarUrl = "Fake",
            id = -1,
            login = "Fake",
        )
    }

    fun users(): List<FollowerModel> {
        return (0..10).map { user() }
    }
}