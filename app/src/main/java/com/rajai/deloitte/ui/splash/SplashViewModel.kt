package com.rajai.deloitte.ui.splash

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rajai.deloitte.view_model.BaseViewModel
import com.rajai.deloitte.view_model.LiveDateEvent

class SplashViewModel : BaseViewModel<Any>() {

    private val _navigateToNextScreen = MutableLiveData<LiveDateEvent<Boolean>>()
    val navigateToNextScreen: LiveData<LiveDateEvent<Boolean>>
        get() = _navigateToNextScreen

    fun startCount() {
        val TIMER_INTERVAL = 1000
        var TIME_IN_MS = 5000 //5 seconds
        object : CountDownTimer(
            TIME_IN_MS.toLong(), TIMER_INTERVAL.toLong()
        ) {
            override fun onTick(millisUntilFinished: Long) {
//                TIME_IN_MS = millisUntilFinished.toInt()
//                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
//                        TimeUnit.MINUTES.toSeconds(
//                            TimeUnit.MILLISECONDS.toMinutes(
//                                millisUntilFinished
//                            )
//                        )
//                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
//                val counter = String.format(Locale.US, "%02d:%02d", minutes, seconds)
//                _countDownTimer.value = LiveDateEvent(counter)
            }

            override fun onFinish() {
                _navigateToNextScreen.value = LiveDateEvent(true)
                _navigateToNextScreen.value = LiveDateEvent(false)
            }

        }.start()
    }
}