package io.github.cam4quality.ui.deviation

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.cam4quality.R
import io.github.cam4quality.network.entity.response.QualityParamDeviationResponseModel
import io.github.cam4quality.ui.BaseIdentifiableAdapter
import io.github.cam4quality.ui.BaseViewHolder
import io.github.cam4quality.utility.`typealias`.Callback
import io.github.cam4quality.utility.extension.inflate
import io.github.cam4quality.utility.extension.lazyBind
import io.github.cam4quality.utility.extension.toFixedNumberString

class QualityParamsDeviationsAdapter(
    private val deviationClickCallback: Callback<QualityParamDeviationResponseModel>
) : BaseIdentifiableAdapter<QualityParamDeviationResponseModel>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<QualityParamDeviationResponseModel> {
        return QualityParamDeviationViewHolder(parent.inflate(R.layout.item_quality_param_deviation))
    }

    inner class QualityParamDeviationViewHolder(view: View) : BaseViewHolder<QualityParamDeviationResponseModel>(view) {

        private val descriptionTextView by lazyBind<TextView>(R.id.item_quality_param_deviation_description)
        private val normalValueTextView by lazyBind<TextView>(R.id.item_quality_param_deviation_normal_value)
        private val minDeviationTextView by lazyBind<TextView>(R.id.item_quality_param_deviation_min_deviation)
        private val maxDeviationTextView by lazyBind<TextView>(R.id.item_quality_param_deviation_max_deviation)

        override fun bind(item: QualityParamDeviationResponseModel) {
            itemView.setOnLongClickListener { deviationClickCallback(item); false }
            with(item) {
                descriptionTextView.text = description
                normalValueTextView.text = normalValue.toFixedNumberString(4)
                minDeviationTextView.text = minValueDeviation.toFixedNumberString(1)
                maxDeviationTextView.text = maxValueDeviation.toFixedNumberString(1)
            }
        }
    }
}