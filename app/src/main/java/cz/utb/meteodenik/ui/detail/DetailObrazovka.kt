package cz.utb.meteodenik.ui.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetailObrazovka(
    viewModel: DetailViewModel = viewModel()
) {
    Scaffold { innerPadding ->
        Text(
            text = "Detail: ${viewModel.mesto?.nazev ?: "Neznámé"}",
            modifier = Modifier.padding(innerPadding).padding(16.dp)
        )
    }
}