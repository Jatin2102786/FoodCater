package com.example.emergency.viewmodels

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.emergency.R
import com.example.emergency.respository.models.RetrofitErrorMessage
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected val isShowLoader = MutableLiveData<Boolean>()
    protected val isShowNoDataText = MutableLiveData<Boolean>()
    protected val isShowSwipeRefreshLayout = MutableLiveData<Boolean>()
    protected val isSessionExpired = MutableLiveData<Boolean>()
    protected val isRefreshTokenExpired = MutableLiveData<Boolean>()
    protected val retrofitErrorDataMessage = MutableLiveData<RetrofitErrorMessage>()
    protected val retrofitErrorMessage = MutableLiveData<RetrofitErrorMessage>()
    protected val successMessage = MutableLiveData<String>()
    protected val errorHandler = MutableLiveData<ErrorHandler>()
    protected val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    fun isShowLoader(): LiveData<Boolean> = isShowLoader

    fun isShowNoDataText(): LiveData<Boolean> = isShowNoDataText

    fun isSessionExpired(): LiveData<Boolean> = isSessionExpired
    fun isRefreshTokenExpired(): LiveData<Boolean> = isRefreshTokenExpired

    fun isShowSwipeRefreshLayout(): LiveData<Boolean> = isShowSwipeRefreshLayout

    fun getRetrofitErrorDataMessage(): LiveData<RetrofitErrorMessage> = retrofitErrorDataMessage

    fun getRetrofitErrorMessage(): LiveData<RetrofitErrorMessage> = retrofitErrorMessage

    fun getErrorHandler(): LiveData<ErrorHandler> = errorHandler

    fun getSuccessMessage(): LiveData<String> = successMessage

    enum class ErrorHandler(@StringRes private val resourceId: Int) : ErrorEvent {
        INVALID_ANSWER(R.string.invalid_answer);

        override fun getErrorResource() = resourceId
    }

    interface ErrorEvent {
        @StringRes
        fun getErrorResource(): Int
    }
}