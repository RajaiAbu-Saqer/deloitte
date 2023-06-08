package com.rajai.deloitte.base_ui

import androidx.core.view.isVisible


open class BaseMainActivity : BaseActivity() {
    fun bottomNavigationVisibility(visible: Boolean) {
        mainBinding.navView.isVisible = visible
    }
}