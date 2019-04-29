package io.github.cam4quality.ui.statistic.factories

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.cam4quality.R
import io.github.cam4quality.network.entity.response.AllFactoriesStatisticsResponseModel
import io.github.cam4quality.ui.BaseRecyclerAdapter
import io.github.cam4quality.ui.BaseViewHolder
import io.github.cam4quality.utility.extension.inflate
import io.github.cam4quality.utility.extension.lazyBind
import io.github.cam4quality.utility.extension.toFixedNumberString

class AllFactoriesAdapter : BaseRecyclerAdapter<AllFactoriesStatisticsResponseModel>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<AllFactoriesStatisticsResponseModel> {
        return AllFactoriesViewHolder(parent.inflate(R.layout.item_factories_statistics))
    }

    class AllFactoriesViewHolder(view: View) : BaseViewHolder<AllFactoriesStatisticsResponseModel>(view) {
        private val countTextView by lazyBind<TextView>(R.id.item_all_factories_details_count)
        private val nameTextView by lazyBind<TextView>(R.id.item_all_factories_factory_name)
        private val failedPercentTextView by lazyBind<TextView>(R.id.item_all_factories_fail_percent)
        private val successPercentTextView by lazyBind<TextView>(R.id.item_all_factories_success_percent)
        override fun bind(item: AllFactoriesStatisticsResponseModel) {
            with(item) {
                countTextView.text = detailsCount.toString()
                nameTextView.text = factoryName
                failedPercentTextView.text = failurePercent.toFixedNumberString(4)
                successPercentTextView.text = successPercent.toFixedNumberString(4)
            }
        }
    }

}