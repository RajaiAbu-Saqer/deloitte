package com.rajai.deloitte.utility

import android.text.TextUtils
import android.util.Patterns
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class StringTools {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    companion object {
        private lateinit var INSTANCE: StringTools
        val instance: StringTools
            get() {
                if (!this::INSTANCE.isInitialized)
                    INSTANCE = StringTools()
                return INSTANCE
            }
    }

    fun isContainSpecChar(string: String) =
        Pattern.compile("[$&+,:;=\\\\?@#|/<>.^*()%!]").matcher(string).find()

    fun isEmptyString(string: String?) = TextUtils.isEmpty(string)
    fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isValidPhoneNumber(phoneNumber: String) =
        phoneNumber.length == 9 && phoneNumber.startsWith("79") || phoneNumber.startsWith("78") || phoneNumber.startsWith(
            "77"
        )

    fun replaceNonstandardDigits(text: String): String {
        if (text.isEmpty()) return text
        fun isNonstandardDigit(ch: Char) = Character.isDigit(ch) && ch !in '0'..'9'
        val builder = StringBuilder()
        text.forEach {
            if (isNonstandardDigit(it)) {
                val numericValue = Character.getNumericValue(it)
                if (numericValue >= 0) builder.append(numericValue)
            } else {
                builder.append(it)
            }
        }
        return builder.toString()
    }

    fun getDateFormatted(date: Calendar) = dateFormatter.format(date.time)
}