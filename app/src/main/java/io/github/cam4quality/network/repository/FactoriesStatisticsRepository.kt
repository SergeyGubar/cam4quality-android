package io.github.cam4quality.network.repository

import io.github.cam4quality.network.api.FactoriesStatisticsApi
import io.github.cam4quality.network.entity.response.*
import io.github.cam4quality.utility.extension.mapToResult
import io.github.cam4quality.utility.extension.workOnBackground
import io.github.cam4quality.utility.helper.SharedPrefHelper
import io.reactivex.Single
import timber.log.Timber

class FactoriesStatisticsRepository(
    private val api: FactoriesStatisticsApi,
    private val prefHelper: SharedPrefHelper
) {
    fun getAllFactoriesStatistics(): Single<Result<List<AllFactoriesStatisticsResponseModel>>> {
        Timber.d("getAllFactoriesStatistics")
        return api.getAllFactoriesStatistics(prefHelper.getBearerToken())
            .mapToResult()
            .workOnBackground()
    }

    fun getMostDangerousFactories(): Single<Result<List<MostDangerousFactoriesStatisticsResponseModel>>> {
        Timber.d("getMostDangerousFactories")
        return api.getMostDangerousFactories(prefHelper.getBearerToken())
            .mapToResult()
            .workOnBackground()
    }

    fun getFactoryFailDailyStatistics(factoryId: String): Single<Result<List<FactoryFailDailyStatisticsResponseModel>>> {
        Timber.d("getFactoryFailDailyStatistics: factoryId = [$factoryId]")
        return api.getFactoryFailDailyStatistics(prefHelper.getBearerToken(), factoryId)
            .mapToResult()
            .workOnBackground()
    }

    fun getFactoryAllTimeStatistics(factoryId: String): Single<Result<FactoryAllTimeStatisticsResponseModel>> {
        Timber.d("getFactoryAllTimeStatistics: factoryId = [$factoryId]")
        return api.getFactoryAllTimeStatistics(prefHelper.getBearerToken(), factoryId)
            .mapToResult()
            .workOnBackground()
    }

    fun getFactoryStatisticsInInterval(
        start: String,
        end: String,
        factoryId: String
    ): Single<Result<List<FactoryStatisticInIntervalResponseModel>>> {
        Timber.d("getFactoryStatisticsInInterval")
        return api.getFactoryStatisticsInInterval(
            prefHelper.getBearerToken(),
            start,
            end,
            factoryId
        ).mapToResult().workOnBackground()
    }
}