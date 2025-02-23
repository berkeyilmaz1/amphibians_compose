package com.example.amphibians.views

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.network.HttpException
import com.example.amphibians.AmphibiansApplication
import com.example.amphibians.data.AmphibiansRepository
import com.example.amphibians.model.Amphibian
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface AmphibiansUiState {
    data class Success(val data: List<Amphibian>) : AmphibiansUiState
    data object Loading : AmphibiansUiState
    data class Error(val message: String) : AmphibiansUiState
}

class AmphibiansViewModel(val amphibiansRepository: AmphibiansRepository) : ViewModel() {

    var amphibiansUiState: AmphibiansUiState by mutableStateOf(AmphibiansUiState.Loading)
        private set

    init {
        getAmphibians()
    }

    private fun getAmphibians() {
        viewModelScope.launch {
            amphibiansUiState = AmphibiansUiState.Loading
            try {
                amphibiansUiState = AmphibiansUiState.Success(amphibiansRepository.getAmphibians())
            } catch (e: IOException) {
                amphibiansUiState = AmphibiansUiState.Error("Error loading amphibians")
            } catch (e: HttpException) {
                amphibiansUiState = AmphibiansUiState.Error("Error loading amphibians")
            } catch (e: Exception) {
                amphibiansUiState = AmphibiansUiState.Error("An error occurred")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as AmphibiansApplication)
                val amphibiansRepository = application.container.amphibiansRepository
                AmphibiansViewModel(amphibiansRepository = amphibiansRepository)
            }
        }
    }
}