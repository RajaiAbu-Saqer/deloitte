package com.rajai.deloitte.base_ui

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.rajai.deloitte.R
import com.rajai.deloitte.enum.SupportedLanguagesEnum
import com.rajai.deloitte.ui.MainActivity
import com.rajai.deloitte.utility.ViewTools

abstract class BaseFragment<VBinding : ViewBinding> : Fragment() {
    protected var binding: VBinding? = null
    protected abstract fun getViewBinding(): VBinding
    protected val mainActivity get() = activity as MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ViewTools.hideKeyboardOnClickingOutside(view, mainActivity)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        init()
        return binding?.root
    }

    private fun init() {
        binding = getViewBinding()
    }

    override fun onAttach(context: Context) {
        val cd = ColorDrawable(
            ContextCompat.getColor(
                context,
                R.color.white
            )
        )
        activity?.window?.setBackgroundDrawable(cd)
        super.onAttach(context)
    }

    protected fun showProgressLoading() {
        if (activity is BaseActivity)
            (activity as BaseActivity).showProgressLoading()
    }

    protected fun hideProgressLoading() {
        if (activity is BaseActivity)
            (activity as BaseActivity).hideProgressLoading()
    }

    protected fun setLanguage(supportedLanguagesEnum: SupportedLanguagesEnum) {
        if (activity is BaseActivity)
            (activity as BaseActivity).setLanguage(supportedLanguagesEnum)
    }

    protected fun navigateTo(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    protected fun popStack() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun reCreateApp() {
        mainActivity.apply {
            finish()
            startActivity(intent)
            overridePendingTransition(R.anim.flip_in_left, R.anim.flip_in_right)
        }
    }
}
