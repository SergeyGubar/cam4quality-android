package io.github.cam4quality.utility.helper

import io.github.cam4quality.network.entity.response.QualityParamDeviationResponseModel
import timber.log.Timber
import kotlin.math.abs

data class DeviationStats(
    val normal: Double,
    val min: Double,
    val max: Double
)

object QualityParamDeviationCalculator {
    fun calculateDeviation(deviation: QualityParamDeviationResponseModel): DeviationStats {
        with(deviation) {
            return DeviationStats(
                normalValue,
                normalValue - normalValue * (minValueDeviation / 100),
                normalValue + normalValue * (maxValueDeviation / 100)
            )
        }
    }

    fun calculacteDeviationPercent(expected: Double, actual: Double): Double {
        return abs(100 - (actual * 100) / expected).also {
            Timber.d("calculacteDeviationPercent: expected = [$expected], actual = [$actual], deviation = [$it]")
        }
    }
}