package com.rajai.deloitte.network

import com.rajai.deloitte.utility.Constants
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET(Constants.API_URL_GET_MOST_POPULAR_VIEWS)
    fun getMostPopularViews(
        @Query("api-key") apiKey: String,
    ): Observable<Any>
}