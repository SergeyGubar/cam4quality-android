package io.github.cam4quality.network.repository

import io.github.cam4quality.network.api.FactoriesApi
import io.github.cam4quality.network.entity.response.FactoryResponseModel
import io.github.cam4quality.utility.extension.mapToResult
import io.github.cam4quality.utility.extension.workOnBackground
import io.github.cam4quality.utility.helper.SharedPrefHelper
import io.reactivex.Single

class FactoriesRepository(
    private val api: FactoriesApi,
    private val prefHelper: SharedPrefHelper
) {
    fun getAllFactories(): Single<Result<List<FactoryResponseModel>?>> {
        return api.getAllFactories(prefHelper.getBearerToken())
            .mapToResult()
            .workOnBackground()
    }
}