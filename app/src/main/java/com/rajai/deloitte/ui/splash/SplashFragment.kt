package com.rajai.deloitte.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.rajai.deloitte.base_ui.BaseMainFragment
import com.rajai.deloitte.databinding.SplashFragmentBinding
import com.rajai.deloitte.view_model.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseMainFragment<SplashFragmentBinding>() {
    private val splashViewModel: SplashViewModel by viewModels()
    override fun getViewBinding() = SplashFragmentBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splashViewModel.startCount()
        observeViewModel()
    }

    private fun observeViewModel() {
        with(splashViewModel) {
            navigateToRegistrationScreen.observeEvent(viewLifecycleOwner) {
                if (it) navigateTo(SplashFragmentDirections.actionNavigationSplashToNavigationRegistration())
            }
            navigateToDashBoardScreen.observeEvent(viewLifecycleOwner) {
                if (it) navigateTo(SplashFragmentDirections.actionSplashFragmentToNavigationDashboard())
            }
        }
    }
}