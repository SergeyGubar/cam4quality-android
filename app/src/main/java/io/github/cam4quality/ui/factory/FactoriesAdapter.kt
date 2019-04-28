package io.github.cam4quality.ui.factory

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.cam4quality.R
import io.github.cam4quality.network.entity.response.FactoryResponseModel
import io.github.cam4quality.ui.BaseAdapter
import io.github.cam4quality.ui.BaseViewHolder
import io.github.cam4quality.utility.`typealias`.Callback
import io.github.cam4quality.utility.extension.lazyBind
import io.github.cam4quality.utility.extension.inflater

class FactoriesAdapter(private val onFactoryClick: Callback<FactoryResponseModel>) :
    BaseAdapter<FactoryResponseModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<FactoryResponseModel> =
        FactoryViewHolder(parent.context.inflater.inflate(R.layout.item_factory, parent, false))

    inner class FactoryViewHolder(view: View) : BaseViewHolder<FactoryResponseModel>(view) {

        private val idTextView by lazyBind<TextView>(R.id.item_factory_id)
        private val nameTextView by lazyBind<TextView>(R.id.item_factory_name)
        private val typeTextView by lazyBind<TextView>(R.id.item_factory_type)
        private val addressTextView by lazyBind<TextView>(R.id.item_factory_address)

        override fun bind(item: FactoryResponseModel) {
            itemView.setOnClickListener { onFactoryClick(item) }
            with(item) {
                idTextView.text = id
                nameTextView.text = name
                typeTextView.text = type
                addressTextView.text = address
            }
        }
    }
}