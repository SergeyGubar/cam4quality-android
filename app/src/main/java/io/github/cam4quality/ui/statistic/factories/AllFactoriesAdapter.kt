package io.github.cam4quality.ui.statistic.factories

import android.view.View
import android.view.ViewGroup
import io.github.cam4quality.R
import io.github.cam4quality.network.entity.response.AllFactoriesStatisticsResponseModel
import io.github.cam4quality.ui.BaseRecyclerAdapter
import io.github.cam4quality.ui.BaseViewHolder
import io.github.cam4quality.utility.extension.inflate

class AllFactoriesAdapter : BaseRecyclerAdapter<AllFactoriesStatisticsResponseModel>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<AllFactoriesStatisticsResponseModel> {
        return AllFactoriesViewHolder(parent.inflate(R.layout.item_factories_statistics))
    }

    class AllFactoriesViewHolder(view: View) : BaseViewHolder<AllFactoriesStatisticsResponseModel>(view) {
        override fun bind(item: AllFactoriesStatisticsResponseModel) {

        }
    }

}