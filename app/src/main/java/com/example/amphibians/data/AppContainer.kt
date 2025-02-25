package com.example.amphibians.data

import com.example.amphibians.network.IAmphibiansApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface AppContainer {
    val amphibiansRepository: AmphibiansRepository
}


class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"



    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: IAmphibiansApiService by lazy {
        retrofit.create(IAmphibiansApiService::class.java)
    }

    override val amphibiansRepository: AmphibiansRepository by lazy {
        AmphibiansRepositoryImpl(retrofitService)
    }
}