package com.rajai.deloitte.network.factory

import io.reactivex.rxjava3.observers.DisposableObserver

abstract class CallbackWrapper<BaseResponse> : DisposableObserver<BaseResponse>() {
    protected abstract fun onSuccess(t: BaseResponse)
    protected abstract fun onFailed(failedObject: String?)
    override fun onNext(t: BaseResponse) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        onFailed(e.message)
    }

    override fun onComplete() {}
}