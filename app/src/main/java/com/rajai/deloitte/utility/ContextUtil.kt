package com.rajai.deloitte.utility

import android.content.Context
import android.content.ContextWrapper

class ContextUtil private constructor(base: Context) : ContextWrapper(base) {
    companion object {
        internal var instance: ContextUtil? = null
        fun init(context: Context) {
            instance = ContextUtil(context.applicationContext)
        }

    }
}
