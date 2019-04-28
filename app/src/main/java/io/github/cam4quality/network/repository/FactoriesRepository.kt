package io.github.cam4quality.network.repository

import io.github.cam4quality.network.api.FactoriesApi
import io.github.cam4quality.network.entity.request.FactoryAddRequestModel
import io.github.cam4quality.network.entity.request.RemoveRequestModel
import io.github.cam4quality.network.entity.response.FactoryResponseModel
import io.github.cam4quality.utility.extension.mapToResult
import io.github.cam4quality.utility.extension.workOnBackground
import io.github.cam4quality.utility.helper.SharedPrefHelper
import io.reactivex.Single
import okhttp3.ResponseBody
import timber.log.Timber

class FactoriesRepository(
    private val api: FactoriesApi,
    private val prefHelper: SharedPrefHelper
) {
    fun getAllFactories(): Single<Result<List<FactoryResponseModel>>> {
        Timber.d("getAllFactories")
        return api.getAllFactories(prefHelper.getBearerToken())
            .mapToResult()
            .workOnBackground()
    }

    fun addFactory(name: String, type: String, address: String): Single<ResponseBody> {
        Timber.d("addFactory: name = [$name], type = [$type], address = [$address]")
        val requestModel = FactoryAddRequestModel(address, name, type)
        return api.addFactory(prefHelper.getBearerToken(), requestModel)
            .workOnBackground()
    }

    fun removeFactory(id: String): Single<ResponseBody> {
        Timber.d("removeFactory: id = [$id]")
        val requestModel = RemoveRequestModel(id)
        return api.removeFactory(prefHelper.getBearerToken(), requestModel)
            .workOnBackground()
    }
}