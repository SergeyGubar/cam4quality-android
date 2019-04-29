package io.github.cam4quality.ui.statistic.faildaily

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.cam4quality.R
import io.github.cam4quality.network.entity.response.FactoryFailDailyStatisticsResponseModel
import io.github.cam4quality.ui.BaseRecyclerAdapter
import io.github.cam4quality.ui.BaseViewHolder
import io.github.cam4quality.utility.extension.inflate
import io.github.cam4quality.utility.extension.lazyBind
import io.github.cam4quality.utility.extension.toFixedNumberString

class FactoryFailDailyAdapter: BaseRecyclerAdapter<FactoryFailDailyStatisticsResponseModel>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<FactoryFailDailyStatisticsResponseModel> {
        return FactoryFailDailyViewHolder(parent.inflate(R.layout.item_factory_fail_daily))
    }

    inner class FactoryFailDailyViewHolder(view: View) : BaseViewHolder<FactoryFailDailyStatisticsResponseModel>(view) {

        private val dayTextView by lazyBind<TextView>(R.id.item_factory_daily_fail_date)
        private val percentTextView by lazyBind<TextView>(R.id.item_factory_daily_fail_percent)

        override fun bind(item: FactoryFailDailyStatisticsResponseModel) {
            with(item) {
                dayTextView.text = date
                percentTextView.text = failPercent.toFixedNumberString(4)
            }
        }
    }
}