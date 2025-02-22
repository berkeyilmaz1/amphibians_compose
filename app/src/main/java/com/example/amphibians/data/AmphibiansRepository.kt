package com.example.amphibians.data

import com.example.amphibians.model.Amphibian
import com.example.amphibians.network.IAmphibiansApiService

interface AmphibiansRepository {
    suspend fun getAmphibians(): List<Amphibian>
}


class AmphibiansRepositoryImpl(private val apiService: IAmphibiansApiService) :
    AmphibiansRepository {
    override suspend fun getAmphibians(): List<Amphibian> = apiService.getAmphibians()

}