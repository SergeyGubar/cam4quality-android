package io.github.cam4quality.ui.statistic.faildaily

import android.view.View
import android.view.ViewGroup
import io.github.cam4quality.R
import io.github.cam4quality.network.entity.response.FactoryFailDailyStatisticsResponseModel
import io.github.cam4quality.ui.BaseRecyclerAdapter
import io.github.cam4quality.ui.BaseViewHolder
import io.github.cam4quality.utility.extension.inflate

class FactoryFailDailyAdapter: BaseRecyclerAdapter<FactoryFailDailyStatisticsResponseModel>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<FactoryFailDailyStatisticsResponseModel> {
        return FactoryFailDailyViewHolder(parent.inflate(R.layout.item_factory_fail_daily))
    }

    inner class FactoryFailDailyViewHolder(view: View) : BaseViewHolder<FactoryFailDailyStatisticsResponseModel>(view) {
        override fun bind(item: FactoryFailDailyStatisticsResponseModel) {

        }
    }
}