package com.example.emergency.respository.models

data class PojoNetworkResponse(val isSuccess: Boolean, val isSessionExpired: Boolean,val isRefreshToken:Boolean
, val isLoginFail: Boolean = false)