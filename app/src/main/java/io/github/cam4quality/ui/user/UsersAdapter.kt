package io.github.cam4quality.ui.user

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.cam4quality.R
import io.github.cam4quality.network.entity.response.UserResponseModel
import io.github.cam4quality.ui.BaseAdapter
import io.github.cam4quality.ui.BaseViewHolder
import io.github.cam4quality.utility.extension.lazyBind
import io.github.cam4quality.utility.extension.inflater

class UsersAdapter(
    private val onUserClick: (UserResponseModel) -> Unit
) : BaseAdapter<UserResponseModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<UserResponseModel> {
        return UserViewHolder(parent.context.inflater.inflate(R.layout.item_user, parent, false))
    }

    inner class UserViewHolder(view: View) : BaseViewHolder<UserResponseModel>(view) {

        private val idTextView by lazyBind<TextView>(R.id.item_user_id)
        private val nameTextView by lazyBind<TextView>(R.id.item_user_name)
        private val emailTextView by lazyBind<TextView>(R.id.item_user_email)
        private val factoryNameTextView by lazyBind<TextView>(R.id.item_user_factory_name)

        override fun bind(item: UserResponseModel) {
            itemView.setOnClickListener { onUserClick(item) }
            with(item) {
                idTextView.text = id
                nameTextView.text = userName
                emailTextView.text = email
                factoryNameTextView.text = factory?.name ?: itemView.context.getString(R.string.no_factory)
            }
        }
    }
}