package com.example.emergency.respository.networkrequest
import com.app.workforce.repository.networkrequests.RestClient
import com.example.emergency.R
import com.example.emergency.respository.models.PojoNetworkResponse
import okhttp3.ResponseBody
import java.io.IOException

object RetrofitRequest {

    fun checkForResponseCode(code: Int): PojoNetworkResponse {
        return when (code) {
            200 -> PojoNetworkResponse(isSuccess = true, isSessionExpired = false,isRefreshToken = false)
            422 -> PojoNetworkResponse(isSuccess = false, isSessionExpired = false,isRefreshToken = false, isLoginFail = true)
            401 -> PojoNetworkResponse(isSuccess = false, isSessionExpired = true,isRefreshToken = false)
            403 -> PojoNetworkResponse(isSuccess = false, isSessionExpired = true,isRefreshToken = false)
            else -> PojoNetworkResponse(isSuccess = false, isSessionExpired = false,isRefreshToken = false)
        }
    }


    fun getErrorMessage(responseBody: ResponseBody): String {
        val errorMessage = ""
        try {
            val errorConverter = RestClient.retrofitInstance!!
                .responseBodyConverter<Error>(Error::class.java, arrayOfNulls<Annotation>(0))
            return errorConverter.convert(responseBody)?.message ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return errorMessage
    }

    fun getRetrofitError(t: Throwable): Int {
        return if (t is IOException) {
            R.string.no_internet
        } else {
            R.string.retrofit_failure
        }
    }

}