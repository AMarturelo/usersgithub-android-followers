package com.amarturelo.usersgithub.followers.presentation.followers.adapter.model

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.amarturelo.usersgithub.core.ext.dp
import com.amarturelo.usersgithub.followers.R
import com.amarturelo.usersgithub.followers.presentation.followers.vo.FollowerListItemVO
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@EpoxyModelClass
abstract class FollowerItemModel : EpoxyModelWithHolder<FollowerItemModel.Holder>() {

    @EpoxyAttribute
    var itemClickedListener: (FollowerListItemVO) -> Unit = { }

    @EpoxyAttribute
    lateinit var item: FollowerListItemVO

    override fun getDefaultLayout(): Int = R.layout.follower_view_item

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.tvFullNameUser.text = item.login
        holder.tvPeopleDescription.text = item.login
        Glide.with(holder.root)
            .load(item.avatarUrl)
            .transform(CenterCrop(), RoundedCorners(16.dp))
            .into(holder.ivUser)

        holder.root.setOnClickListener {
            itemClickedListener(item)
        }
    }

    class Holder : EpoxyHolder() {
        lateinit var root: View
        lateinit var ivUser: ImageView
        lateinit var tvFullNameUser: TextView
        lateinit var tvPeopleDescription: TextView
        override fun bindView(itemView: View) {
            root = itemView
            ivUser = itemView.findViewById(R.id.ivFollowerListItem)
            tvFullNameUser = itemView.findViewById(R.id.tvFollowerListItemFullName)
            tvPeopleDescription = itemView.findViewById(R.id.tvFollowerListItemDescription)
        }
    }
}
