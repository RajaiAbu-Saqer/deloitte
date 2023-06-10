package com.rajai.deloitte.di

import com.rajai.deloitte.BuildConfig
import com.rajai.deloitte.network.AddHeaderInterceptor
import com.rajai.deloitte.network.BackendApi
import com.rajai.deloitte.network.TokenAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideNetwork() = getNetworkClient()
    private fun getNetworkClient() =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(getTrustedOkHttpClient().build())
            .build()

    private fun getTrustedOkHttpClient(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        builder.apply {
            followRedirects(false)
            readTimeout(100, TimeUnit.SECONDS)
            writeTimeout(100, TimeUnit.SECONDS)
            connectTimeout(100, TimeUnit.SECONDS)
            hostnameVerifier { _, _ -> true }
            addNetworkInterceptor(AddHeaderInterceptor())
            authenticator(TokenAuthenticator())
        }
        return builder
    }

    @Singleton
    @Provides
    fun provideBackendApi() = BackendApi(provideNetwork())
}

