package cz.utb.meteodenik.data

import cz.utb.meteodenik.model.Mesto
import cz.utb.meteodenik.model.NalezeneMesto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object MestaZdroj {
    private val _mesta = MutableStateFlow(
        listOf(
            Mesto(id = 1, nazev = "Zlín", sirka = 49.2265, delka = 17.6683),
            Mesto(id = 2, nazev = "Praha", sirka = 50.0755, delka = 14.4378),
            Mesto(id = 3, nazev = "Brno", sirka = 49.1951, delka = 16.6068)
        )
    )

    val mesta: StateFlow<List<Mesto>> = _mesta.asStateFlow()

    fun pridej(nalezene: NalezeneMesto) {
        _mesta.update { soucasna ->
            val noveId = (soucasna.maxOfOrNull { it.id } ?: 0) + 1
            soucasna + Mesto(
                id = noveId,
                nazev = nalezene.nazev,
                sirka = nalezene.sirka,
                delka = nalezene.delka
            )
        }
    }

    fun podleId(id: Int): Mesto? = _mesta.value.find { it.id == id }
}