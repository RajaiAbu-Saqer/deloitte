package com.rajai.deloitte.ui

import android.os.Bundle
import android.view.View
import com.rajai.deloitte.base_ui.BaseMainFragment
import com.rajai.deloitte.databinding.FragmentMoreBinding

class MoreFragment : BaseMainFragment<FragmentMoreBinding>() {
    override fun getViewBinding() = FragmentMoreBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}