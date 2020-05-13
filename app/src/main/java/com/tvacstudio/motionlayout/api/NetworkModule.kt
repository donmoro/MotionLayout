package com.tvacstudio.motionlayout.api

import com.tvacstudio.motionlayout.api.services.FitnessApi
import com.tvacstudio.motionlayout.config.RemoteConfig
import com.tvacstudio.motionlayout.config.RemoteConfig.ConnectionConstants.CONNECTION_TIME_OUT_IN_SECONDS
import com.tvacstudio.motionlayout.config.RemoteConfig.ConnectionConstants.READ_TIME_OUT_IN_SECONDS
import com.tvacstudio.motionlayout.config.RemoteConfig.ConnectionConstants.WRITE_TIME_OUT_IN_SECONDS
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    private lateinit var httpClient: OkHttpClient.Builder
    private lateinit var retrofit: Retrofit

    fun init() {

        httpClient = OkHttpClient.Builder()

        val httpLoggingInterceptor = HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger.DEFAULT
        )
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addInterceptor(httpLoggingInterceptor)

        httpClient.connectTimeout(CONNECTION_TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)

        retrofit = Retrofit.Builder()
            .baseUrl(RemoteConfig.baseUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

    }

    private fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }

    val fitnessApi: FitnessApi
        get() = createService(FitnessApi::class.java)

}