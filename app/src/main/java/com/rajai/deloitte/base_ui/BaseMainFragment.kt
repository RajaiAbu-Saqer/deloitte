package com.rajai.deloitte.base_ui

import androidx.viewbinding.ViewBinding
import com.rajai.deloitte.MainActivity

abstract class BaseMainFragment<VBinding : ViewBinding> : BaseFragment<VBinding>() {
    protected val mainActivity get() = activity as MainActivity
    private fun bottomNavigationVisibility(visibility: Boolean) =
        mainActivity.bottomNavigationVisibility(visibility)

    override fun onResume() {
        bottomNavigationVisibility(mainActivity.isMainFragments())
        super.onResume()
    }
}