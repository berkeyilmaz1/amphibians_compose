package com.example.amphibians.views.amphibians

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.amphibians.R
import com.example.amphibians.model.Amphibian
import com.example.amphibians.theme.AmphibiansTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import coil3.compose.AsyncImage
import coil3.request.ImageRequest

@Composable
fun AmphibiansView(
    amphibiansUiState: AmphibiansUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (amphibiansUiState) {
        is AmphibiansUiState.Success -> {
            AmphibiansList(
                amphibians = amphibiansUiState.data,
                modifier = modifier,
                contentPadding = contentPadding
            )
        }

        is AmphibiansUiState.Loading -> {
            LoadingScreen(modifier = modifier.padding(contentPadding))
        }

        is AmphibiansUiState.Error -> {
            ErrorScreen(retryAction, modifier = modifier.padding(contentPadding))
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AmphibiansList(
    amphibians: List<Amphibian>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.medium)),
        state = rememberLazyListState()
    ) {
        items(
            items = amphibians,
            key = { amphibian -> amphibian.name },

            ) { amphibian ->
            AmphibianCard(
                amphibian = amphibian,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}


@Composable
fun AmphibianCard(amphibian: Amphibian, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(R.dimen.small))
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                stringResource(
                    R.string.amphibian_title,
                    amphibian.name,
                    amphibian.type
                ), modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.medium)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.image_height)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(amphibian.img_src)
                    .build(),
                contentDescription = stringResource(R.string.amphibian_image),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.loading_img),
                error = painterResource(R.drawable.ic_broken_image)
            )
            Text(
                text = amphibian.description,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(dimensionResource(R.dimen.medium))
            )
        }
    }
}


@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = stringResource(
                R.string.connection_error
            )
        )
        Text(
            stringResource(R.string.loading_failed),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(dimensionResource(R.dimen.medium))
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    AmphibiansTheme {
        LoadingScreen(
            Modifier
                .fillMaxSize()

        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    AmphibiansTheme {
        ErrorScreen({}, Modifier.fillMaxSize())
    }
}