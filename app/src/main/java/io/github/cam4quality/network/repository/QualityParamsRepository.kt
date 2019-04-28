package io.github.cam4quality.network.repository

import io.github.cam4quality.network.api.QualityParamsApi
import io.github.cam4quality.network.entity.request.QualityParamRequestModel
import io.github.cam4quality.network.entity.request.RemoveRequestModel
import io.github.cam4quality.network.entity.response.QualityParamResponseModel
import io.github.cam4quality.utility.extension.mapToResult
import io.github.cam4quality.utility.extension.workOnBackground
import io.github.cam4quality.utility.helper.SharedPrefHelper
import io.reactivex.Single
import okhttp3.ResponseBody
import timber.log.Timber

class QualityParamsRepository(
    private val api: QualityParamsApi,
    private val prefHelper: SharedPrefHelper
) {
    fun getAllQualityParams(): Single<Result<List<QualityParamResponseModel>>> {
        Timber.d("getAllQualityParams")
        return api.getAllQualityParams(prefHelper.getBearerToken())
            .mapToResult()
            .workOnBackground()
    }

    fun addQualityParam(name: String, deviationId: String, value: Double): Single<ResponseBody> {
        Timber.d("addQualityParam: name = [$name], deviationId = [$deviationId], value = [$value]")
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

    fun removeParam(id: String): Single<ResponseBody> {
        Timber.d("removeParam: id = [$id]")
        val removeModel = RemoveRequestModel(id)
        return api.removeQualityParam(prefHelper.getBearerToken(), removeModel)
            .workOnBackground()
    }
}