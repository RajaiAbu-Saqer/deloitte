package com.rajai.deloitte.ui.registration

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.rajai.deloitte.R
import com.rajai.deloitte.base_ui.BaseMainFragment
import com.rajai.deloitte.databinding.FragmentRegistrationBinding
import com.rajai.deloitte.generic.GenericPagerAdapter

class RegistrationFragment : BaseMainFragment<FragmentRegistrationBinding>() {
    override fun getViewBinding() = FragmentRegistrationBinding.inflate(layoutInflater)
    private val tapLayoutTexts by lazy {
        listOf(getString(R.string.login), getString(R.string.registration))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabLayout()
        preparePagerAdapter()
    }

    private fun initTabLayout() {
        binding?.tabLayout?.apply {
            tapLayoutTexts.forEach { tapText ->
                binding?.tabLayout?.newTab()?.setText(tapText)?.let { addTab(it) }
            }
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    binding?.viewPager?.currentItem = tab?.position ?: 0
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    private fun preparePagerAdapter() {
        binding?.viewPager?.isUserInputEnabled = false
        object : GenericPagerAdapter(
            fragmentActivity = mainActivity,
            binding?.viewPager,
            listOf(SigninFragment(), SignupFragment())
        ) {}
    }
}