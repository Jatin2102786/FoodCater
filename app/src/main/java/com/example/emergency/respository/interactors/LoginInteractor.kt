package com.example.emergency.respository.interactors
import com.app.workforce.repository.networkrequests.RestClient
import com.example.emergency.respository.networkrequest.NetworkRequestCallbacks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


class LoginInteractor {
    // Login
    fun login(
        postSignup: MutableMap<String, String?>,
        networkRequestCallbacks: NetworkRequestCallbacks
    ): Disposable {
        return RestClient.get().login(postSignup)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Response<*>>() {
                override fun onNext(response: Response<*>) {
                    networkRequestCallbacks.onSuccess(response)
                }
                override fun onError(t: Throwable) {
                    networkRequestCallbacks.onError(t)
                }
                override fun onComplete() {
                }
            })
    }
}