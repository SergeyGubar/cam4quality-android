package io.github.cam4quality.network.api

import io.github.cam4quality.network.constant.NetworkConstants
import io.github.cam4quality.network.entity.request.QualityParamDeviationRequestModel
import io.github.cam4quality.network.entity.response.QualityParamDeviationResponseModel
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface QualityParamsDeviationsApi {
    @GET("/api/allQualityParamDeviations")
    fun getAllQualityParamsDeviations(@Header(NetworkConstants.HEADER_AUTHORIZATION) token: String): Single<Response<List<QualityParamDeviationResponseModel>>>

    @POST("/api/addQualityParamDeviation")
    fun addQualityParamDeviation(
        @Header(NetworkConstants.HEADER_AUTHORIZATION) token: String,
        @Body qualityParamRequestModel: QualityParamDeviationRequestModel
    ): Single<ResponseBody>
}