package io.github.cam4quality.network.repository

import io.github.cam4quality.network.api.DetailsApi
import io.github.cam4quality.network.entity.request.RemoveRequestModel
import io.github.cam4quality.network.entity.response.DetailResponseModel
import io.github.cam4quality.utility.extension.mapToResult
import io.github.cam4quality.utility.extension.workOnBackground
import io.github.cam4quality.utility.helper.SharedPrefHelper
import io.reactivex.Single
import okhttp3.ResponseBody
import timber.log.Timber

class DetailsRepository(
    private val api: DetailsApi,
    private val prefHelper: SharedPrefHelper
) {
    fun getAllDetails(): Single<Result<List<DetailResponseModel>>> {
        return api.getAllDetails(prefHelper.getBearerToken())
            .mapToResult()
            .workOnBackground()
    }

    fun removeDetail(
        id: String
    ): Single<ResponseBody> {
        Timber.d("removeQualityParamDeviation")
        val requestModel = RemoveRequestModel(id)
        return api.removeDetail(prefHelper.getBearerToken(), requestModel)
            .workOnBackground()
    }
}