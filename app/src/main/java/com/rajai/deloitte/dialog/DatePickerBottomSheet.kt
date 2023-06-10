package com.rajai.deloitte.dialog

import android.widget.LinearLayout
import android.widget.NumberPicker
import androidx.core.view.children
import androidx.fragment.app.FragmentActivity
import com.rajai.deloitte.R
import com.rajai.deloitte.databinding.DatePickerDialogBinding
import com.rajai.deloitte.generic.GeneralBottomSheetDialog
import com.rajai.deloitte.utility.StringTools
import java.util.*

abstract class DatePickerBottomSheet(private val fragmentActivity: FragmentActivity) :
    GeneralBottomSheetDialog<DatePickerDialogBinding>(fragmentActivity) {
    override fun onLayoutCreated(
        view: GeneralBottomSheetDialog<DatePickerDialogBinding>
    ) {
        binding.apply {
            ((datePicker.getChildAt(0) as LinearLayout).getChildAt(0) as LinearLayout).children.forEachIndexed { i, it ->
                val label = when (i) {
                    0 -> monthTxt
                    1 -> dayTxt
                    else -> yearTxt
                }
                label.text = when ((it as NumberPicker).maxValue) {
                    11 -> fragmentActivity.getString(R.string.month)
                    in 1000..9999 -> fragmentActivity.getString(R.string.year)
                    else -> fragmentActivity.getString(R.string.day)
                }

                it.setFormatter { StringTools.instance.replaceNonstandardDigits(it.toString()) }
            }
            submitDate.setOnClickListener {
                submit(this)
                dismiss()
            }
        }

    }

    abstract fun submitDate(date: String)
    private fun submit(datePickerDialogBinding: DatePickerDialogBinding) {
        datePickerDialogBinding.datePicker.apply {
            val calendar = Calendar.getInstance()
            val day = dayOfMonth
            val month = month
            val year = year
            calendar.set(year, month, day)
            submitDate(StringTools.instance.getDateFormatted(calendar))
        }
    }
}