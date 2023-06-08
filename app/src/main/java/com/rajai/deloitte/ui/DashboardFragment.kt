package com.rajai.deloitte.ui

import android.os.Bundle
import android.view.View
import com.rajai.deloitte.base_ui.BaseMainFragment
import com.rajai.deloitte.databinding.FragmentDashboardBinding

class DashboardFragment : BaseMainFragment<FragmentDashboardBinding>() {
    override fun getViewBinding() = FragmentDashboardBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}