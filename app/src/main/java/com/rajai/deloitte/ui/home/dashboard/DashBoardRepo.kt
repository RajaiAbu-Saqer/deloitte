package com.rajai.deloitte.ui.home.dashboard

import com.rajai.deloitte.network.APIStatusCallback
import com.rajai.deloitte.network.BackendApi
import com.rajai.deloitte.utility.GsonUtils.getObjectFromGson

object DashBoardRepo {
    fun callGetMostPopularViewsApi(
        backendApi: BackendApi,
        onSuccess: (results: List<Results>) -> Unit,
        onFail: (failureMessage: String?) -> Unit,
    ) {
        backendApi.callGetMostPopularViewsApi(object : APIStatusCallback {
            override fun onSuccess(response: Any) {
                val data = getObjectFromGson(
                    response, DashBoardResponseModel::class.java
                ).results
                onSuccess(data)
            }

            override fun onFailed(failureMessage: String?) {
                onFail(failureMessage)
            }
        })
    }
}