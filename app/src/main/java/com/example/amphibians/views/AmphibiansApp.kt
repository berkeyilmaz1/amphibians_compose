package com.example.amphibians.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amphibians.R
import com.example.amphibians.views.amphibians.AmphibiansView
import com.example.amphibians.views.amphibians.AmphibiansViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmphibiansApp() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding()),
            color = MaterialTheme.colorScheme.background
        ) {
            val amphibiansViewModel: AmphibiansViewModel =
                viewModel(factory = AmphibiansViewModel.Factory)
            AmphibiansView(
                modifier = Modifier.fillMaxSize(),
                amphibiansUiState = amphibiansViewModel.amphibiansUiState,
                contentPadding = innerPadding,
                retryAction = amphibiansViewModel::getAmphibians
            )
        }
    }
}