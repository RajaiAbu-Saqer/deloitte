package com.rajai.deloitte.base_ui

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible


open class BaseMainActivity : BaseActivity(), OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    fun bottomNavigationVisibility(visible: Boolean) {
        mainBinding.navView.isVisible = visible
    }

    fun headerText(title: String) {
        mainBinding.viewToolBar?.title?.apply {
            visibility = View.VISIBLE
            text = title
        }
    }

    fun toolbarVisibility(visible: Boolean) {
        mainBinding.viewToolBar.toolBar.apply {
            visibility = if (visible) View.VISIBLE
            else View.GONE
        }
    }

    private fun init() {
        mainBinding.viewToolBar.back.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            mainBinding.viewToolBar.back.id -> onBackPressed()
        }
    }
}