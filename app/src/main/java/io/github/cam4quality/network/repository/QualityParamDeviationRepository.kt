package io.github.cam4quality.network.repository

import io.github.cam4quality.network.api.QualityParamsDeviationsApi
import io.github.cam4quality.network.entity.request.QualityParamDeviationRequestModel
import io.github.cam4quality.network.entity.response.QualityParamDeviationResponseModel
import io.github.cam4quality.utility.extension.mapToResult
import io.github.cam4quality.utility.extension.workOnBackground
import io.github.cam4quality.utility.helper.SharedPrefHelper
import io.reactivex.Single
import okhttp3.ResponseBody

class QualityParamsDeviationsRepository(
    private val api: QualityParamsDeviationsApi,
    private val prefHelper: SharedPrefHelper
) {
    fun getAllQualityParamsDeviations(): Single<Result<List<QualityParamDeviationResponseModel>>> {
        return api.getAllQualityParamsDeviations(prefHelper.getBearerToken())
            .mapToResult()
            .workOnBackground()
    }

    fun addQualityParamDeviation(
        description: String,
        maxValue: Double,
        minValue: Double,
        normalValue: Double
    ): Single<ResponseBody> {
        val requestModel = QualityParamDeviationRequestModel(
            description,
            maxValue,
            minValue,
            normalValue
        )
        return api.addQualityParamDeviation(
            prefHelper.getBearerToken(),
            requestModel
        ).workOnBackground()
    }
}