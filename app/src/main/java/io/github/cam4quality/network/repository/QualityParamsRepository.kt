package io.github.cam4quality.network.repository

import io.github.cam4quality.network.api.QualityParamsApi
import io.github.cam4quality.network.entity.request.QualityParamRequestModel
import io.github.cam4quality.network.entity.response.QualityParamResponseModel
import io.github.cam4quality.utility.extension.mapToResult
import io.github.cam4quality.utility.extension.workOnBackground
import io.github.cam4quality.utility.helper.SharedPrefHelper
import io.reactivex.Single
import okhttp3.ResponseBody

class QualityParamsRepository(
    private val api: QualityParamsApi,
    private val prefHelper: SharedPrefHelper
) {
    fun getAllQualityParams(): Single<Result<List<QualityParamResponseModel>>> {
        return api.getAllQualityParams(prefHelper.getBearerToken())
            .mapToResult()
            .workOnBackground()
    }

    fun addQualityParam(name: String, deviationId: String, value: Double): Single<ResponseBody> {
        val qualityParamRequestModel = QualityParamRequestModel(
            name,
            deviationId,
            value
        )
        return api.addQualityParam(
            prefHelper.getBearerToken(),
            qualityParamRequestModel
        ).workOnBackground()
    }
}