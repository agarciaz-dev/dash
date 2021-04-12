package com.eelseth.network.config

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import toothpick.InjectConstructor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
@InjectConstructor
class ServiceProvider {

    private val baseUrl = "https://api.doordash.com/v1/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(baseUrl)
            .client(okHttpClient())
            .addConverterFactory(MoshiConverterFactory.create()).build()
    }

    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(networkLoggingInterceptorProvider())
            .build()
    }

    private fun networkLoggingInterceptorProvider(): Interceptor {
        return HttpLoggingInterceptor { message -> Log.i("Network", message) }.apply {
            level = HttpLoggingInterceptor.Level.BODY
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }
}