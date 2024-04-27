package com.example.emergency.viewmodels

import android.app.Application
import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.emergency.R
import com.example.emergency.respository.interactors.LoginInteractor
import com.example.emergency.respository.models.LoginResponseModel
import com.example.emergency.respository.models.ResponseModelUser
import com.example.emergency.respository.models.RetrofitErrorMessage
import com.example.emergency.respository.networkrequest.NetworkRequestCallbacks
import com.example.emergency.respository.networkrequest.RetrofitRequest
import retrofit2.Response
import java.util.*

class LoginViewModel(application: Application) : BaseViewModel(application) {
    private val isLoginSuccess = MutableLiveData<ResponseModelUser>()
    private val isMessage = MutableLiveData<String>()
    private val isGetProgress = MutableLiveData<Boolean>()
    private val mLoginInteractor by lazy { LoginInteractor() }

    // Login api
    fun login(email: String, password: String) {
        if (TextUtils.isEmpty(email)) {
            isMessage.value = "PLease enter your email address"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isMessage.value = "PLease enter valid email address"
        } else if (TextUtils.isEmpty(password)) {
            isMessage.value = "Please enter your password"
        }  else if (password.length > 10) {
            isMessage.value = "Password Should not be more than 10 Character"
        } else if (password.length < 8) {
            isMessage.value = "Password Should not be less than 8 Character"
        } else {
            val params: MutableMap<String, String?> =
                HashMap()
            params["email"] = email
            params["password"] = password
            params["device_token"] = "idddmmd74339584"
            params["device_type"] = "android"
            isGetProgress.value = true
            mCompositeDisposable.add(
                mLoginInteractor.login(
                    params, networkRequestCallbacks = object :
                        NetworkRequestCallbacks {
                        override fun onSuccess(response: Response<*>) {
                            try {
                                isGetProgress.value = false
                                val pojoNetworkResponse = RetrofitRequest
                                    .checkForResponseCode(response.code())

                                when {
                                    pojoNetworkResponse.isSuccess && null != response.body() -> {
                                        val login =
                                            response.body() as LoginResponseModel
                                        if(login.status)
                                        {
                                            isLoginSuccess.value = login.data.user
                                        }
                                        else
                                        {
                                            isMessage.value = "User credentials doesn't matched"
                                        }

                                    }

                                    pojoNetworkResponse.isLoginFail ->
                                    {
                                        isMessage.value = "User credentials doesn't matched"
                                    }
                                    else -> {
                                        retrofitErrorMessage
                                            .postValue(
                                                RetrofitErrorMessage(
                                                    errorMessage =
                                                    RetrofitRequest.getErrorMessage(response.errorBody()!!)
                                                )
                                            )
                                    }
                                }

                            } catch (e: Exception) {
                                isGetProgress.value = false
                                e.printStackTrace()
                                retrofitErrorMessage
                                    .postValue(
                                        RetrofitErrorMessage(
                                            errorResId =
                                            R.string.retrofit_failure
                                        )
                                    )
                            }

                        }

                        override fun onError(t: Throwable) {
                            t.printStackTrace()
                            isGetProgress.value = false
                            retrofitErrorMessage
                                .postValue(
                                    RetrofitErrorMessage(
                                        errorResId =
                                        RetrofitRequest.getRetrofitError(t)
                                    )
                                )
                        }
                    })
            )
        }
    }
    fun onGetLoginSuccess(): LiveData<ResponseModelUser> = isLoginSuccess
    fun onGetMessage(): LiveData<String> = isMessage
    fun onGetProgress(): LiveData<Boolean> = isGetProgress
}