package com.amarturelo.usersgithub.followers.utils

import com.amarturelo.usersgithub.followers.domain.entity.FollowerEntity


object FakeValuesEntity {
    fun user(): FollowerEntity {
        return FollowerEntity(
            "Fake",
            -1,
            "Fake",
        )
    }

    fun users(): List<FollowerEntity> {
        return (0..10).map { user() }
    }
}