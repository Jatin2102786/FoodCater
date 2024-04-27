package com.example.emergency.respository.networkrequest

import retrofit2.Response

interface NetworkRequestCallbacks {

    fun onSuccess(response: Response<*>)

    fun onError(t: Throwable)

}