package cz.utb.meteodenik.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.utb.meteodenik.DetailMestaActivity
import cz.utb.meteodenik.R
import cz.utb.meteodenik.ZaznamUlozenReceiver

@Composable
fun SeznamMestObrazovka() {
    val context = LocalContext.current

    Scaffold { insety ->
        Column(modifier = Modifier.padding(insety).padding(16.dp)) {
            // 1. Заголовок (считаем реальное количество городов в списке)
            Text(
                text = pluralStringResource(
                    id = R.plurals.pocet_zaznamu,
                    count = UKAZKOVA_MESTA.size,
                    UKAZKOVA_MESTA.size
                ),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 2. Карточка геолокации
            PolohaKarta(onPolohaPovolena = {})

            // 3. Твой цикл вывода городов
            UKAZKOVA_MESTA.forEach { mesto ->
                Column(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.pozadi_karty)) // Темная/Светлая тема
                        .clickable {
                            // Интент с данными конкретного города
                            val intent = Intent(context, DetailMestaActivity::class.java).apply {
                                putExtra(DetailMestaActivity.EXTRA_MESTO, mesto.nazev)
                                putExtra(DetailMestaActivity.EXTRA_TEPLOTA, mesto.teplota)
                            }
                            context.startActivity(intent)
                        }
                        .padding(16.dp)
                ) {
                    Text(text = mesto.nazev, style = MaterialTheme.typography.titleMedium)
                    Text(text = stringResource(R.string.teplota_format, mesto.teplota))
                }
            }

            // 4. Кнопка Broadcast (оставляем внизу для тестов)
            Button(
                onClick = {
                    context.sendBroadcast(
                        Intent(ZaznamUlozenReceiver.ACTION_ZAZNAM_ULOZEN).apply {
                            setPackage(context.packageName)
                            putExtra(ZaznamUlozenReceiver.EXTRA_MESTO, "Zlín")
                        }
                    )
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(stringResource(R.string.poslat_zpravu))
            }
        }
    }
}
// Вставь это в самый конец файла
data class Mesto(val nazev: String, val teplota: Double)

val UKAZKOVA_MESTA = listOf(
    Mesto("Zlín", 21.4),
    Mesto("Praha", 19.5),
    Mesto("Brno", 22.1)
)