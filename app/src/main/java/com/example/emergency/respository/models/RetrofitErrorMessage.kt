package com.example.emergency.respository.models

import androidx.annotation.StringRes

data class RetrofitErrorMessage(
    @StringRes val errorResId: Int? = null,
    val errorMessage: String? = null
)