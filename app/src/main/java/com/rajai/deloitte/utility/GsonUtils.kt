package com.rajai.deloitte.utility

import com.google.gson.Gson

object GsonUtils {
    fun <T> getObjectFromGson(response: Any, classOfT: Class<T>): T {
        val gson = Gson()
        return gson.fromJson(gson.toJson(response), classOfT)
    }
}