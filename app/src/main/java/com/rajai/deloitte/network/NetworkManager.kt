package com.rajai.deloitte.network

import com.rajai.deloitte.BuildConfig
import com.rajai.deloitte.network.factory.CallbackWrapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import kotlin.reflect.KFunction

class NetworkManager(retrofit: Retrofit) {
    private val apiInterface = retrofit.create(ApiInterface::class.java)



     fun callGetMostPopularViewsApi(
        apiCall: KFunction<Observable<Any>>,
        apiCallback: APIStatusCallback,
    ) {
        apiCall.call(apiInterface, BuildConfig.API_KEY).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CallbackWrapper<Any>() {
                override fun onSuccess(t: Any) {
                    apiCallback.onSuccess(t)
                }

                override fun onFailed(failedObject: String?) {
                    apiCallback.onFailed(failedObject)
                }
            })
    }
}