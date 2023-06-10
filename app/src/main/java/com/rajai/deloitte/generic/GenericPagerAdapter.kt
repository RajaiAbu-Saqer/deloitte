package com.rajai.deloitte.generic

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

open class GenericPagerAdapter(
    fragmentActivity: FragmentActivity,
    viewPager: ViewPager2?,
    var fragmentList: List<Fragment>
) : FragmentStateAdapter(fragmentActivity) {
    init {
        viewPager?.apply {
            offscreenPageLimit = fragmentList.size
            adapter = this@GenericPagerAdapter
        }
    }

    override fun getItemCount() = fragmentList.size
    override fun createFragment(position: Int): Fragment = fragmentList[position]
}