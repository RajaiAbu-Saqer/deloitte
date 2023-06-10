package com.rajai.deloitte.base_ui

import androidx.viewbinding.ViewBinding

abstract class BaseMainFragment<VBinding : ViewBinding> : BaseFragment<VBinding>() {
    protected open fun showToolBar() = false
    protected open fun initToolBarItems() {}
    private fun bottomNavigationVisibility(visibility: Boolean) =
        mainActivity.bottomNavigationVisibility(visibility)

    protected fun headerText(title: String) = mainActivity.headerText(title)
    override fun onResume() {
        super.onResume()
        refreshToolbar()
    }

    fun toolbarVisibility(visible: Boolean) = mainActivity.toolbarVisibility(visible)

    private fun refreshToolbar() {
        bottomNavigationVisibility(mainActivity.isMainFragments())
        mainActivity.toolbarVisibility(showToolBar())
        initToolBarItems()
    }
}