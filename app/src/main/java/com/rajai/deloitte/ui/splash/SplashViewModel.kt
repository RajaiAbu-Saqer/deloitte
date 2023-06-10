package com.rajai.deloitte.ui.splash

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rajai.deloitte.utility.CryptoPrefsUtil
import com.rajai.deloitte.view_model.BaseViewModel
import com.rajai.deloitte.view_model.LiveDateEvent

class SplashViewModel : BaseViewModel<Any>() {
    private val _navigateToRegistrationScreen = MutableLiveData<LiveDateEvent<Boolean>>()
    val navigateToRegistrationScreen: LiveData<LiveDateEvent<Boolean>>
        get() = _navigateToRegistrationScreen

    private val _navigateToDashBoardScreen = MutableLiveData<LiveDateEvent<Boolean>>()
    val navigateToDashBoardScreen: LiveData<LiveDateEvent<Boolean>>
        get() = _navigateToDashBoardScreen

    fun startCount() {
        val timerInterval = 1000
        val timeInMillSec = 3000
        object : CountDownTimer(
            timeInMillSec.toLong(), timerInterval.toLong()
        ) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                if (CryptoPrefsUtil.instance.getString(CryptoPrefsUtil.AUTHORIZATION)
                        ?.isNotEmpty() == true
                ) {
                    _navigateToDashBoardScreen.value = LiveDateEvent(true)
                    _navigateToDashBoardScreen.value = LiveDateEvent(false)
                } else {
                    _navigateToRegistrationScreen.value = LiveDateEvent(true)
                    _navigateToRegistrationScreen.value = LiveDateEvent(false)
                }
            }


        }.start()
    }
}