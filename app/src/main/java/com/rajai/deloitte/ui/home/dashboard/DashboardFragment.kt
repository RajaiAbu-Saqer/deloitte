package com.rajai.deloitte.ui.home.dashboard

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.rajai.deloitte.base_ui.BaseMainFragment
import com.rajai.deloitte.databinding.FilterBottomSheetBinding
import com.rajai.deloitte.databinding.FragmentDashboardBinding
import com.rajai.deloitte.generic.GeneralBottomSheetDialog
import com.rajai.deloitte.view_model.observeEvent
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DashboardFragment : BaseMainFragment<FragmentDashboardBinding>(), OnClickListener {
    private val dashboardViewModel by viewModels<DashboardViewModel>()
    override fun getViewBinding() = FragmentDashboardBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        callDashBoardApi()
        initViewModelObservers()
        swipeToRefreshDashBoard()
        searchFiltration()
    }

    private fun swipeToRefreshDashBoard() {
        binding?.swipeRefresh?.setOnRefreshListener {
            callDashBoardApi()
        }
    }

    private fun init() {
        binding?.filter?.setOnClickListener(this)
    }

    private fun searchFiltration() {
        binding?.searchEtx?.doOnTextChanged { text, _, _, _ ->
            if (newsAdapter?.itemsList?.isNotEmpty() == true) {
                binding?.dashboardRecycler?.suppressLayout(false)
                val text = text?.toString() ?: ""
                newsAdapter?.filter?.filter(text.lowercase(Locale.ROOT).trim())
            }

        }
    }

    private fun initViewModelObservers() {
        with(dashboardViewModel) {
            progressStatus.observeEvent(viewLifecycleOwner) {
                if (it) showProgressLoading() else {
                    binding?.swipeRefresh?.isRefreshing = false
                    hideProgressLoading()
                }
            }
            showError.observeEvent(viewLifecycleOwner) {
                Toast.makeText(mainActivity, it, Toast.LENGTH_SHORT).show()
            }
            dashboardResult.observeEvent(viewLifecycleOwner) {
                getDashBoardAdapter()?.clear()?.add(it)?.refresh()
            }
        }
    }

    private fun callDashBoardApi() {
        dashboardViewModel.callGetMostPopularViewsApi()
    }

    private var newsAdapter: DashboardNewsAdapter? = null
    private fun getDashBoardAdapter(): DashboardNewsAdapter? {
        newsAdapter = binding?.dashboardRecycler?.let { recycler ->
            DashboardNewsAdapter(recycler, { result ->
                navigateTo(
                    DashboardFragmentDirections.actionNavigationDashboardToNavigationNewsDetails(
                        result
                    )
                )
                binding?.searchEtx?.setText("")
            }, { results ->
                binding?.noResultTxt?.isVisible = results.isEmpty()
            })
        }
        return newsAdapter
    }

    private fun filterBottomSheet() {
        object : GeneralBottomSheetDialog<FilterBottomSheetBinding>(mainActivity) {
            override fun onLayoutCreated(
                view: GeneralBottomSheetDialog<FilterBottomSheetBinding>
            ) {
                binding.apply {
                    setContentView(root)
                    close.setOnClickListener {
                        dismiss()
                    }
                    applyFilter.setOnClickListener { dismiss() }
                }
            }

            override fun getViewBinding() = FilterBottomSheetBinding.inflate(layoutInflater)
        }.show()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding?.filter?.id -> filterBottomSheet()
        }
    }
}