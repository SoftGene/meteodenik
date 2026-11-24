package cz.utb.meteodenik.ui.mesta

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cz.utb.meteodenik.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeznamMestObrazovka(
    viewModel: MestaViewModel = viewModel(),
    onMestoKliknuto: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.mesta_titulek)) },
                actions = {
                    TextButton(onClick = { viewModel.obnovit() }) {
                        Text(stringResource(R.string.akce_obnovit))
                    }
                }
            )
        }
    ) { innerPadding ->
        SeznamMestObsah(
            uiState = uiState,
            onMestoClick = { id ->
                viewModel.vyberMesto(id)
                onMestoKliknuto(id)
            },
            onZkusitZnovu = { viewModel.obnovit() },
            modifier = Modifier.padding(innerPadding)
        )
    }
}