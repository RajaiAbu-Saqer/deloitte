package com.rajai.deloitte.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.bumptech.glide.Glide
import com.rajai.deloitte.R

class ProgressBarLoading(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.progress_bar)
        setCanceledOnTouchOutside(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        Glide.with(context).load(R.drawable.progress_loader_gif)
            .into(findViewById(R.id.progressBar))
        setCancelable(false)
    }
}