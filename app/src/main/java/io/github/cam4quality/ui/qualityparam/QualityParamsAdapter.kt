package io.github.cam4quality.ui.qualityparam

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.github.cam4quality.R
import io.github.cam4quality.network.entity.response.QualityParamResponseModel
import io.github.cam4quality.ui.BaseAdapter
import io.github.cam4quality.ui.BaseViewHolder
import io.github.cam4quality.utility.extension.inflate
import io.github.cam4quality.utility.extension.lazyBind
import io.github.cam4quality.utility.extension.makeVisible
import io.github.cam4quality.utility.validator.QualityValidator

class QualityParamsAdapter(private val onQualityParamClick: (QualityParamResponseModel) -> Unit) :
    BaseAdapter<QualityParamResponseModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<QualityParamResponseModel> =
        QualityParamViewHolder(parent.inflate(R.layout.item_quality_param))

    inner class QualityParamViewHolder(view: View) : BaseViewHolder<QualityParamResponseModel>(view) {

        private val idTextView by lazyBind<TextView>(R.id.item_quality_param_id)
        private val nameTextView by lazyBind<TextView>(R.id.item_quality_param_name)
        private val valueTextView by lazyBind<TextView>(R.id.item_quality_param_value)
        private val failImageView by lazyBind<ImageView>(R.id.item_quality_param_fail_circle)

        override fun bind(item: QualityParamResponseModel) {
            itemView.setOnClickListener { onQualityParamClick(item) }
            with(item) {
                idTextView.text = id
                nameTextView.text = name
                valueTextView.text = value.toString()
                if (!QualityValidator.isValid(item)) failImageView.makeVisible()
            }
        }
    }
}