package com.rajai.deloitte.utility

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object ViewTools {
    @SuppressLint("ClickableViewAccessibility")
    fun hideKeyboardOnClickingOutside(view: View, activity: Activity) {
        if (view !is EditText) {
            view.setOnTouchListener { _: View?, event: MotionEvent? ->
                hideKeyboard(activity)
                false
            }
        }
    }

    private fun hideKeyboard(activity: Activity) {
        val view = activity.currentFocus
        view?.let {
            val imm =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

    }

    fun firstLastNameMinLengthValidation(
        editText: EditText,
        requiredMessage: String,
        lengthMessage: String,
        inValidMessage: String,
    ): Boolean {
        editText.text.trim().toString().apply {
            if (isEmpty()) {
                showEditTextError(editText, requiredMessage)
                return true
            } else if (length < 3) {
                showEditTextError(editText, lengthMessage)
                return true
            }

            if (containNumber(this) || StringTools.instance.isContainSpecChar(this)) {
                showEditTextError(editText, inValidMessage)
                return true
            }
        }
        return false
    }

    private fun showEditTextError(editText: EditText?, errorText: String?) {
        editText?.let {
            it.apply {
                setError(errorText, null)
                requestFocus()
            }
        }
    }

    private fun containNumber(text: String) = text.matches(".*\\d.*".toRegex())
    fun isEmptyEditText(editText: EditText, errorText: String): Boolean {
        editText.apply {
            if (StringTools.instance.isEmptyString(text.toString().trim())) {
                showEditTextError(this, errorText)
                return true
            }
            return false
        }
    }

    fun isValidEmail(editText: EditText, errorText: String): Boolean {
        editText.apply {
            if (!StringTools.instance.isValidEmail(text.toString().trim())) {
                showEditTextError(this, errorText)
                return true
            }
            return false
        }
    }

    fun isValidPhoneNumber(editText: EditText, errorText: String): Boolean {
        editText.apply {
            if (!StringTools.instance.isValidPhoneNumber(text.toString().trim())) {
                showEditTextError(this, errorText)
                return true
            }
            return false
        }
    }

    fun isEditTextCharLessThan(
        editText: EditText,
        minChar: Int,
        errorText: String,
    ): Boolean {
        editText.apply {
            if (text.toString().trim().length < minChar) {
                showEditTextError(this, errorText)
                return true
            }
            return false
        }
    }

    fun isComplexPassword(password: EditText, inValidMessage: String): Boolean {
        return if (password.text.toString()
            matches ("(?=^.{8,}\$)(?=.*\\d)(?=.*[\$&+,:;=\\\\?@#|/<>.~^*()%!]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$".toRegex())
        )
            true
        else {
            showEditTextError(password, inValidMessage)
            false
        }
    }

    fun removeEditTextError(editText: EditText) {
        editText.apply {
            error = null
            clearFocus()
        }
    }

}