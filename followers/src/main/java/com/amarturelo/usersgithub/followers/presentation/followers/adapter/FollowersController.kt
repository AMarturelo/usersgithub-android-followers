package com.amarturelo.usersgithub.followers.presentation.followers.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.amarturelo.usersgithub.followers.presentation.followers.adapter.model.FollowerItemModel_
import com.amarturelo.usersgithub.followers.presentation.followers.vo.FollowerListItemVO

class FollowersController(
    private val itemClickedListener: (FollowerListItemVO) -> Unit = {},
) :
    TypedEpoxyController<List<FollowerListItemVO>>() {

    override fun buildModels(employees: List<FollowerListItemVO>) {
        for (item in employees) {
            FollowerItemModel_()
                .id(item.id.toString())
                .item(item)
                .itemClickedListener(itemClickedListener)
                .addTo(this)
        }
    }
}
