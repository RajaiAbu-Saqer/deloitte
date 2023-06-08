package com.rajai.deloitte.view_model

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

open class LiveDateEvent<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandledOrReturnNull(): T? {
        return if (hasBeenHandled)
            null
        else {
            hasBeenHandled = false
            content
        }
    }

    fun peekContent(): T = content
}

fun <T> LiveData<out LiveDateEvent<T>>.observeEvent(
    owner: LifecycleOwner,
    onEventUnhandled: (T) -> Unit
) {
    observe(
        owner
    ) { it?.getContentIfNotHandledOrReturnNull()?.let(onEventUnhandled) }
}
