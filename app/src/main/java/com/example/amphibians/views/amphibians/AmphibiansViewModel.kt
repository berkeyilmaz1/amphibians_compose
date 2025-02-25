package com.example.amphibians.views.amphibians

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.AmphibiansApplication
import com.example.amphibians.R
import com.example.amphibians.data.AmphibiansRepository
import com.example.amphibians.model.Amphibian
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

sealed interface AmphibiansUiState {
    data class Success(val data: List<Amphibian>) : AmphibiansUiState
    object Loading : AmphibiansUiState
    data class Error(val message: Int) : AmphibiansUiState
}

class AmphibiansViewModel(val amphibiansRepository: AmphibiansRepository) : ViewModel() {

    var amphibiansUiState: AmphibiansUiState by mutableStateOf(AmphibiansUiState.Loading)
        private set

    init {
        getAmphibians()
    }

    fun getAmphibians() {
        amphibiansUiState = AmphibiansUiState.Loading
        viewModelScope.launch {
            amphibiansUiState = try {
                withContext(Dispatchers.IO) {
                    val result = amphibiansRepository.getAmphibians()
                    AmphibiansUiState.Success(result)
                }
            } catch (e: IOException) {
                Log.e("AmphibiansViewModel", "IO Exception: ${e.message}")
                AmphibiansUiState.Error(R.string.connection_error)
            } catch (e: Exception) {
                Log.e("AmphibiansViewModel", "Unexpected error: ${e.message}")
                AmphibiansUiState.Error(R.string.error)
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