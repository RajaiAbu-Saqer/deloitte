package com.rajai.deloitte.network

import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import javax.inject.Inject
import kotlin.reflect.KFunction

class BackendApi @Inject constructor(retrofit: Retrofit) {
    private val networkManager = NetworkManager(retrofit)

    companion object {
        val MOST_POPULAR_VIEWS_API_CALL: KFunction<Observable<Any>> =
            ApiInterface::getMostPopularViews
    }

    fun callGetMostPopularViewsApi(apiCallback: APIStatusCallback) {
        networkManager.callGetMostPopularViewsApi(
            MOST_POPULAR_VIEWS_API_CALL, apiCallback
        )
    }
}