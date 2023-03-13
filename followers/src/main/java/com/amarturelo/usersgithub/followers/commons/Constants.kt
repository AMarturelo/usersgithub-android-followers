package com.amarturelo.usersgithub.followers.commons

interface Constants {
    object API {
        const val PATH_USERNAME = "username"

        const val FOLLOWERS = "/users/{$PATH_USERNAME}/followers"
    }

    object ARG{
        const val USERNAME = "username_arg"
    }
}