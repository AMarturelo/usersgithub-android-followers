package com.amarturelo.usersgithub.followers.utils

import com.amarturelo.usersgithub.followers.presentation.followers.vo.FollowerListItemVO


object FakeValuesVO {
    fun user(): FollowerListItemVO {
        return FollowerListItemVO(
            -1,
            "Fake",
            "Fake",
        )
    }

    fun users(): List<FollowerListItemVO> {
        return (0..10).map { user() }
    }
}