package cz.utb.meteodenik.ui.mesta

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.utb.meteodenik.R
import cz.utb.meteodenik.model.Mesto
import cz.utb.meteodenik.ui.theme.MeteoDenikTheme
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MestoKarta(
    mesto: Mesto,
    jeVybrano: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        border = if (jeVybrano) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null,
        colors = CardDefaults.cardColors(
            containerColor = if (jeVybrano) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = mesto.nazev, style = MaterialTheme.typography.titleMedium)
            val teplotaText = if (mesto.teplotaC != null) {
                stringResource(R.string.mesto_teplota, mesto.teplotaC)
            } else {
                stringResource(R.string.mesto_chybi_teplota)
            }
            Text(text = teplotaText)
        }
    }
}

@Composable
fun PridatMestoRadek(
    nazev: String,
    onNazevChange: (String) -> Unit,
    onPridat: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = nazev,
            onValueChange = onNazevChange,
            label = { Text(stringResource(R.string.mesto_pole_label)) },
            modifier = Modifier.weight(1f).padding(end = 8.dp)
        )
        Button(
            onClick = onPridat,
            enabled = nazev.isNotBlank()
        ) {
            Text(stringResource(R.string.mesto_pridat))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SeznamMestObsah(
    uiState: MestaUiState,
    onPridat: (String) -> Unit,
    onMestoClick: (Int) -> Unit
) {
    // Состояние текстового поля теперь живет здесь
    var novyNazev by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.mesta_titulek)) })
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            PridatMestoRadek(
                nazev = novyNazev,
                onNazevChange = { novyNazev = it },
                onPridat = {
                    onPridat(novyNazev)
                    novyNazev = ""
                }
            )
            HorizontalDivider()
            if (uiState.mesta.isEmpty()) {
                Text(
                    text = stringResource(R.string.mesta_prazdno),
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                    items(items = uiState.mesta, key = { it.id }) { mesto ->
                        MestoKarta(
                            mesto = mesto,
                            jeVybrano = uiState.vybraneMestoId == mesto.id,
                            onClick = { onMestoClick(mesto.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SeznamMestObrazovka(
    viewModel: MestaViewModel = viewModel(),
    onMestoKliknuto: (Int) -> Unit
) {
    // Подписываемся на StateFlow правильно!
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SeznamMestObsah(
        uiState = uiState,
        onPridat = { nazev -> viewModel.pridejMesto(nazev) },
        onMestoClick = { id ->
            viewModel.vyberMesto(id) // Сохраняем в ViewModel (SavedStateHandle)
            onMestoKliknuto(id)      // Даем сигнал NavHost'у переключить экран
        }
    )
}

// --- ПРЕВЬЮ ---
val ukazkovaMesta = listOf(
    Mesto(id = 1, nazev = "Zlín", teplotaC = 21.4),
    Mesto(id = 2, nazev = "Praha", teplotaC = 19.5),
    Mesto(id = 3, nazev = "Brno", teplotaC = null)
)

@Preview(showBackground = true, name = "1. Světlý")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "2. Tmavý")
@Composable
fun SeznamMestObsahPreview() {
    MeteoDenikTheme {
        SeznamMestObsah(
            uiState = MestaUiState(mesta = ukazkovaMesta, vybraneMestoId = 2), // Прага выбрана
            onPridat = {},
            onMestoClick = {}
        )
    }
}