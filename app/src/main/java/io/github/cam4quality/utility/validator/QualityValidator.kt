package io.github.cam4quality.utility.validator

import io.github.cam4quality.network.entity.response.QualityParamResponseModel
import io.github.cam4quality.utility.helper.QualityParamDeviationCalculator
import timber.log.Timber

object QualityValidator {
    fun isValid(qualityParam: QualityParamResponseModel): Boolean {
        val deviationStats = QualityParamDeviationCalculator.calculateDeviation(qualityParam.paramDeviation)
        val min = deviationStats.min
        val max = deviationStats.max
        return (qualityParam.value in min..max).also {
            Timber.d("qualityParamValue = [${qualityParam.value}], minAllowedValue = [$min], maxAllowedValue = [$max],  isValid = [$it]")
        }
    }
}