package com.example.amphibians.data

import com.example.amphibians.network.IAmphibiansApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val amphibiansRepository: AmphibiansRepository
}


class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/amphibians"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()


    private val retrofitService: IAmphibiansApiService by lazy {
        retrofit.create(IAmphibiansApiService::class.java)
    }
    override val amphibiansRepository: AmphibiansRepository by lazy {
        AmphibiansRepositoryImpl(retrofitService)
    }

}