package io.github.cam4quality.network.api

import io.github.cam4quality.network.constant.NetworkConstants
import io.github.cam4quality.network.entity.request.QualityParamDeviationRequestModel
import io.github.cam4quality.network.entity.request.RemoveRequestModel
import io.github.cam4quality.network.entity.response.QualityParamDeviationResponseModel
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface QualityParamsDeviationsApi {
    @GET("/api/allQualityParamDeviations")
    fun getAllQualityParamsDeviations(@Header(NetworkConstants.HEADER_AUTHORIZATION) token: String): Single<Response<List<QualityParamDeviationResponseModel>>>

    @POST("/api/addQualityParamDeviation")
    fun addQualityParamDeviation(
        @Header(NetworkConstants.HEADER_AUTHORIZATION) token: String,
        @Body qualityParamRequestModel: QualityParamDeviationRequestModel
    ): Single<ResponseBody>


    @HTTP(method = "DELETE", path = "/api/removeQualityParamDeviation", hasBody = true)
    fun removeQualityParamDeviation(
        @Header(NetworkConstants.HEADER_AUTHORIZATION) token: String,
        @Body removeModel: RemoveRequestModel
    ): Single<ResponseBody>

}