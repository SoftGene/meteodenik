package cz.utb.meteodenik.ui.mesta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.utb.meteodenik.R

@Composable
fun SeznamMestObsah(
    uiState: MestaUiState,
    onMestoClick: (Int) -> Unit,
    onZkusitZnovu: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        if (uiState.nacitaSe) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        if (uiState.chyba != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = chybovyText(uiState.chyba),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = onZkusitZnovu) {
                    Text(stringResource(R.string.akce_zkusit_znovu))
                }
            }
        }

        if (uiState.mesta.isEmpty()) {
            Text(
                text = stringResource(R.string.mesta_prazdno),
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                items(items = uiState.mesta, key = { it.mesto.id }) { polozka ->
                    MestoKarta(
                        polozka = polozka,
                        jeVybrano = uiState.vybraneMestoId == polozka.mesto.id,
                        onClick = { onMestoClick(polozka.mesto.id) }
                    )
                }
            }
        }
    }
}