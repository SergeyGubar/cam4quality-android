package io.github.cam4quality.network.api

import io.github.cam4quality.network.constant.NetworkConstants
import io.github.cam4quality.network.entity.response.*
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface FactoriesStatisticsApi {

    @GET("/api/getAllFactoriesStatistics")
    fun getAllFactoriesStatistics(
        @Header(NetworkConstants.HEADER_AUTHORIZATION) token: String
    ): Single<Response<List<AllFactoriesStatisticsResponseModel>>>

    @GET("/api/getMostDangerousFactories")
    fun getMostDangerousFactories(
        @Header(NetworkConstants.HEADER_AUTHORIZATION) token: String
    ): Single<Response<List<MostDangerousFactoriesStatisticsResponseModel>>>

    @GET("/api/factoryFailDailyStatistic")
    fun getFactoryFailDailyStatistics(
        @Header(NetworkConstants.HEADER_AUTHORIZATION) token: String,
        @Query("factoryId") factoryId: String
    ): Single<Response<List<FactoryFailDailyStatisticsResponseModel>>>

    @GET("/api/factoryAllTimeStatistic")
    fun getFactoryAllTimeStatistics(
        @Header(NetworkConstants.HEADER_AUTHORIZATION) token: String,
        @Query("factoryId") factoryId: String
    ): Single<Response<FactoryAllTimeStatisticsResponseModel>>


    @GET("/api/factoryStatisticInInterval")
    fun getFactoryStatisticsInInterval(
        @Header(NetworkConstants.HEADER_AUTHORIZATION) token: String,
        @Query("start") start: String,
        @Query("end") end: String,
        @Query("factoryId") factoryId: String
    ): Single<Response<FactoryStatisticInIntervalResponseModel>>

}