package com.rajai.deloitte.ui.home.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rajai.deloitte.network.BackendApi
import com.rajai.deloitte.view_model.BaseViewModel
import com.rajai.deloitte.view_model.LiveDateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val backendApi: BackendApi) :
    BaseViewModel<Any>() {
    private val _progressStatus = MutableLiveData<LiveDateEvent<Boolean>>()
    val progressStatus: LiveData<LiveDateEvent<Boolean>> get() = _progressStatus
    private val _showError = MutableLiveData<LiveDateEvent<String>>()
    val showError: LiveData<LiveDateEvent<String>> get() = _showError
    private val _dashboardResult = MutableLiveData<LiveDateEvent<List<Results>?>>()
    val dashboardResult: LiveData<LiveDateEvent<List<Results>?>> get() = _dashboardResult
    fun callGetMostPopularViewsApi() {
        _progressStatus.value = LiveDateEvent(true)
        DashBoardRepo.callGetMostPopularViewsApi(backendApi = backendApi, onSuccess = { result ->
            this._dashboardResult.value = LiveDateEvent(result)
            _progressStatus.value = LiveDateEvent(false)
        }, onFail = { failureMessage ->
            _progressStatus.value = LiveDateEvent(false)
            _showError.value = LiveDateEvent(failureMessage ?: "")
        })
    }
}