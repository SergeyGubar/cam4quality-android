package io.github.cam4quality.ui.statistic.mostdangerous

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.cam4quality.R
import io.github.cam4quality.network.entity.response.MostDangerousFactoriesStatisticsResponseModel
import io.github.cam4quality.ui.BaseRecyclerAdapter
import io.github.cam4quality.ui.BaseViewHolder
import io.github.cam4quality.utility.extension.inflate
import io.github.cam4quality.utility.extension.lazyBind
import io.github.cam4quality.utility.extension.toFixedNumberString

class MostDangerousFactoriesAdapter : BaseRecyclerAdapter<MostDangerousFactoriesStatisticsResponseModel>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<MostDangerousFactoriesStatisticsResponseModel> {
        return MostDangerousFactoryViewHolder(parent.inflate(R.layout.item_most_dangerous_factory))
    }

    inner class MostDangerousFactoryViewHolder(view: View) :
        BaseViewHolder<MostDangerousFactoriesStatisticsResponseModel>(view) {

        private val factoryNameTextView by lazyBind<TextView>(R.id.item_most_dangerous_factory_name)
        private val factoryFailPercentTextView by lazyBind<TextView>(R.id.item_most_dangerous_factory_fail_percent)

        override fun bind(item: MostDangerousFactoriesStatisticsResponseModel) {
            with(item) {
                factoryNameTextView.text = factoryName
                factoryFailPercentTextView.text = failPercent.toFixedNumberString(4)
            }
        }
    }
}