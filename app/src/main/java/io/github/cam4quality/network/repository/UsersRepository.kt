package io.github.cam4quality.network.repository

import io.github.cam4quality.network.api.UsersApi
import io.github.cam4quality.network.entity.request.UserReisterRequestModel
import io.github.cam4quality.network.entity.response.UserResponseModel
import io.github.cam4quality.utility.extension.mapToResult
import io.github.cam4quality.utility.extension.workOnBackground
import io.github.cam4quality.utility.helper.SharedPrefHelper
import io.reactivex.Single
import okhttp3.ResponseBody

class UsersRepository(
    private val api: UsersApi,
    private val prefHelper: SharedPrefHelper
) {
    fun getAllUsers(): Single<Result<List<UserResponseModel>>> {
        return api.getAllUsers(prefHelper.getBearerToken())
            .mapToResult()
            .workOnBackground()
    }

    fun register(userName: String, email: String, password: String, factoryId: String): Single<ResponseBody> {
        val userRegisterRequestModel = UserReisterRequestModel(email, userName, factoryId, password)
        return api.register(userRegisterRequestModel)
            .workOnBackground()
    }
}