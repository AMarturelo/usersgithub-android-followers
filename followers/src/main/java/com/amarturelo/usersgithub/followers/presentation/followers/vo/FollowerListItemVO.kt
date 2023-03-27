package com.amarturelo.usersgithub.followers.presentation.followers.vo

import android.os.Parcelable
import com.amarturelo.usersgithub.followers.domain.entity.FollowerEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FollowerListItemVO(val id: Int, val login: String, val avatarUrl: String) : Parcelable

fun FollowerEntity.toVO(): FollowerListItemVO = FollowerListItemVO(id, login, avatarUrl)
