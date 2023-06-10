package com.rajai.deloitte.network

interface APIStatusCallback {
    fun onSuccess(response: Any)
    fun onFailed(failureMessage: String?)
}