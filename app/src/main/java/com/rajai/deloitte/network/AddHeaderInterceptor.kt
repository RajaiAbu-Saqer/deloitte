package com.rajai.deloitte.network

import com.rajai.deloitte.utility.Constants
import com.rajai.deloitte.utility.CryptoPrefsUtil
import com.rajai.deloitte.utility.CryptoPrefsUtil.Companion.instance
import okhttp3.Interceptor
import okhttp3.Response

class AddHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        builder.addHeader(
            Constants.AUTHORIZATION,
            instance.getString(CryptoPrefsUtil.AUTHORIZATION) ?: ""
        )
        return chain.proceed(builder.build())
    }

}