package io.github.cam4quality.ui.detail

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.github.cam4quality.R
import io.github.cam4quality.network.entity.response.DetailResponseModel
import io.github.cam4quality.ui.BaseAdapter
import io.github.cam4quality.ui.BaseViewHolder
import io.github.cam4quality.utility.extension.inflate
import io.github.cam4quality.utility.extension.lazyBind

class DetailsAdapter : BaseAdapter<DetailResponseModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<DetailResponseModel> =
        DetailViewHolder(parent.inflate(R.layout.item_detail))

    inner class DetailViewHolder(view: View) : BaseViewHolder<DetailResponseModel>(view) {

        private val detailImageView by lazyBind<ImageView>(R.id.item_detail_image)
        private val detailDateTextView by lazyBind<TextView>(R.id.item_detail_date_value)
        private val detailFactoryTextView by lazyBind<TextView>(R.id.item_detail_factory_value)
        private val detailIsCriticalTextView by lazyBind<TextView>(R.id.item_detail_is_critical_value)

        override fun bind(item: DetailResponseModel) {
            with(item) {
                // I'll burn in hell for doing this on main thread
                val decodedString = Base64.decode(this.photo.content, Base64.DEFAULT)
                val decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                detailImageView.setImageBitmap(decodedBitmap)
                detailDateTextView.text = dateTime
                detailFactoryTextView.text = factory.name
                detailIsCriticalTextView.text = if (isCritical) itemView.context.getString(R.string.yes) else itemView.context.getString(R.string.no)
            }
        }
    }
}