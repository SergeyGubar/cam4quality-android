package io.github.cam4quality.utility.helper

import android.content.Context
import androidx.appcompat.app.AlertDialog
import io.github.cam4quality.R
import io.github.cam4quality.utility.extension.toFixedNumberString

object DialogUtil {
    fun showQualityParamDialog(
        context: Context,
        paramValue: Double,
        normalValue: Double,
        maxDeviation: Double,
        minDeviation: Double,
        failPercent: Double
    ) {
        AlertDialog.Builder(context)
            .setTitle(R.string.quality_param_info_title)
            .setMessage(
                context.getString(
                    R.string.quality_param_info,
                    paramValue.toFixedNumberString(4),
                    normalValue.toFixedNumberString(4),
                    maxDeviation.toFixedNumberString(4),
                    minDeviation.toFixedNumberString(4),
                    failPercent.toFixedNumberString(4)
                )
            )
            .setPositiveButton(R.string.ok, null)
            .show()
    }
}