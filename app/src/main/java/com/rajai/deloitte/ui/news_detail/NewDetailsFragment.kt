package com.rajai.deloitte.ui.news_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.rajai.deloitte.R
import com.rajai.deloitte.base_ui.BaseMainFragment
import com.rajai.deloitte.databinding.FragmentNewsDetailsBinding
import com.rajai.deloitte.utility.DrawableTools

class NewDetailsFragment : BaseMainFragment<FragmentNewsDetailsBinding>() {
    private val newsArgs by navArgs<NewDetailsFragmentArgs>()
    override fun getViewBinding() = FragmentNewsDetailsBinding.inflate(layoutInflater)
    override fun initToolBarItems() {
        super.initToolBarItems()
        toolbarVisibility(true)
        headerText(getString(R.string.news_details))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        binding?.image?.setOnClickListener {
            navigateTo(NewDetailsFragmentDirections.actionNavigationNewsDetailsToNavigationMore())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        newsArgs.newsModel?.let { result ->
            binding?.description?.text =
                "${result.type}\n${result.title}\n${result.abstract}\n${result.published_date}\n${result.updated}"
            binding?.image?.let { image ->
                DrawableTools.setImageUrl(
                    image,
                    result.media?.firstOrNull()?.mediaMetadata?.firstOrNull()?.url ?: ""
                )
            }
        }

    }
}