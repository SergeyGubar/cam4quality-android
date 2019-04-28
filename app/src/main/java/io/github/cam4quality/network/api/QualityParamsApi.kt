package io.github.cam4quality.network.api

import io.github.cam4quality.network.constant.NetworkConstants
import io.github.cam4quality.network.entity.request.QualityParamRequestModel
import io.github.cam4quality.network.entity.request.RemoveRequestModel
import io.github.cam4quality.network.entity.response.QualityParamResponseModel
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface QualityParamsApi {
    @GET("/api/allQualityParams")
    fun getAllQualityParams(@Header(NetworkConstants.HEADER_AUTHORIZATION) token: String): Single<Response<List<QualityParamResponseModel>>>

    @POST("/api/addQualityParam")
    fun addQualityParam(
        @Header(NetworkConstants.HEADER_AUTHORIZATION) token: String,
        @Body qualityParamRequestModel: QualityParamRequestModel
    ): Single<ResponseBody>

    @HTTP(method = "DELETE", path = "/api/removeQualityParam", hasBody = true)
    fun removeQualityParam(
        @Header(NetworkConstants.HEADER_AUTHORIZATION) token: String,
        @Body removeModel: RemoveRequestModel
    ): Single<ResponseBody>
}