package io.github.cam4quality.network.repository

import io.github.cam4quality.network.api.LoginApi
import io.github.cam4quality.network.entity.request.LoginRequestModel
import io.github.cam4quality.network.entity.response.LoginResponseModel
import io.github.cam4quality.utility.extension.mapToResult
import io.github.cam4quality.utility.extension.workOnBackground
import io.reactivex.Single

class LoginRepository(private val api: LoginApi) {
    fun login(email: String, password: String): Single<Result<LoginResponseModel?>> {
        val loginRequestModel = LoginRequestModel(email, password)
        return api.login(loginRequestModel)
            .mapToResult()
            .workOnBackground()
    }
}