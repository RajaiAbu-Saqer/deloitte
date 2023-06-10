package com.rajai.deloitte.generic

import android.content.res.Resources
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rajai.deloitte.R

abstract class GeneralBottomSheetDialog<VBinding : ViewBinding>(activity: FragmentActivity) :
    BottomSheetDialog(activity) {
    private var isDismissible = false
    private var isCancellable = false
    private var isFullScreen = true
    protected lateinit var binding: VBinding
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
//        val binding =
//            FilterBottomSheetBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawableResource(R.color.transparent)
        }
        if (isFullScreen)
            behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels

        behavior.isHideable = isDismissible
        setCancelable(isCancellable)
        onLayoutCreated(this)
    }

    fun fullScreen(): GeneralBottomSheetDialog<VBinding> {
        isFullScreen = true
        return this
    }

    fun dismissible(): GeneralBottomSheetDialog<VBinding> {
        isDismissible = true
        return this
    }

    fun cancellable(): GeneralBottomSheetDialog<VBinding> {
        isCancellable = true
        return this
    }

    protected abstract fun getViewBinding(): VBinding
    protected abstract fun onLayoutCreated(view: GeneralBottomSheetDialog<VBinding>)

}