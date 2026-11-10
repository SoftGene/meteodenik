package cz.utb.meteodenik.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.utb.meteodenik.R
import cz.utb.meteodenik.model.Mesto
import cz.utb.meteodenik.ui.theme.MeteoDenikTheme

val ukazkovaMesta = listOf(
    Mesto(id = 1, nazev = "Zlín", teplotaC = 21.4),
    Mesto(id = 2, nazev = "Praha", teplotaC = 19.5),
    Mesto(id = 3, nazev = "Brno", teplotaC = null)
)

@Composable
fun MestoKarta(mesto: Mesto) {
    // Пока оставляем простую карточку, дизайн обновим позже
    Text(
        text = mesto.nazev,
        modifier = Modifier.padding(16.dp)
    )
}

// 1. Компонент строки добавления города (без состояния)
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
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        Button(
            onClick = onPridat,
            enabled = nazev.isNotBlank()
        ) {
            Text(stringResource(R.string.mesto_pridat))
        }
    }
}

// 2. Компонент списка городов
@Composable
fun MestaSeznam(mesta: List<Mesto>) {
    if (mesta.isEmpty()) {
        Text(
            text = stringResource(R.string.mesta_prazdno),
            modifier = Modifier.padding(16.dp)
        )
    } else {
        LazyColumn {
            items(items = mesta, key = { mesto -> mesto.id }) { mesto ->
                MestoKarta(mesto = mesto)
            }
        }
    }
}

// 3. Безсостоятельный каркас экрана (Scaffold)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MestaObsah(
    mesta: List<Mesto>,
    novyNazev: String,
    onNovyNazevChange: (String) -> Unit,
    onPridat: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.mesta_titulek)) })
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            PridatMestoRadek(
                nazev = novyNazev,
                onNazevChange = onNovyNazevChange,
                onPridat = onPridat
            )
            HorizontalDivider()
            MestaSeznam(mesta = mesta)
        }
    }
}

// 4. Компонент с состоянием (Stateful) - Точка входа для экрана
@Composable
fun MestaObrazovka() {
    var novyNazev by rememberSaveable { mutableStateOf("") }
    val mesta = remember { mutableStateListOf(*ukazkovaMesta.toTypedArray()) }

    MestaObsah(
        mesta = mesta,
        novyNazev = novyNazev,
        onNovyNazevChange = { novyNazev = it },
        onPridat = {
            val noveId = (mesta.maxOfOrNull { it.id } ?: 0) + 1
            mesta.add(Mesto(id = noveId, nazev = novyNazev.trim(), teplotaC = null))
            novyNazev = ""
        }
    )
}

// --- ПРЕВЬЮ ---
@Preview(showBackground = true, name = "1. Světlý")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "2. Tmavý")
@Composable
fun MestaObsahPreview() {
    MeteoDenikTheme {
        MestaObsah(
            mesta = ukazkovaMesta,
            novyNazev = "",
            onNovyNazevChange = {},
            onPridat = {}
        )
    }
}

@Preview(showBackground = true, name = "3. Prázdný")
@Composable
fun MestaObsahPrazdnyPreview() {
    MeteoDenikTheme {
        MestaObsah(
            mesta = emptyList(),
            novyNazev = "",
            onNovyNazevChange = {},
            onPridat = {}
        )
    }
}