package io.github.cam4quality.network.repository

import io.github.cam4quality.network.api.UsersApi
import io.github.cam4quality.network.entity.request.RemoveRequestModel
import io.github.cam4quality.network.entity.request.UserRegisterRequestModel
import io.github.cam4quality.network.entity.response.UserResponseModel
import io.github.cam4quality.utility.extension.mapToResult
import io.github.cam4quality.utility.extension.workOnBackground
import io.github.cam4quality.utility.helper.SharedPrefHelper
import io.reactivex.Single
import okhttp3.ResponseBody
import timber.log.Timber

class UsersRepository(
    private val api: UsersApi,
    private val prefHelper: SharedPrefHelper
) {
    fun getAllUsers(): Single<Result<List<UserResponseModel>>> {
        Timber.d("getAllUsers")
        return api.getAllUsers(prefHelper.getBearerToken())
            .mapToResult()
            .workOnBackground()
    }

    fun register(userName: String, email: String, password: String, factoryId: String): Single<ResponseBody> {
        Timber.d("register: userName = [$userName], email = [$email], password = [$password], factoryId = [$factoryId]")
        val userRegisterRequestModel = UserRegisterRequestModel(email, userName, factoryId, password)
        return api.register(userRegisterRequestModel)
            .workOnBackground()
    }

    fun removeUser(id: String): Single<ResponseBody> {
        Timber.d("removeUser: id = [$id]")
        val removeModel = RemoveRequestModel(id)
        return api.removeUser(prefHelper.getBearerToken(), removeModel)
            .workOnBackground()
    }
}