package cz.utb.meteodenik.data

import cz.utb.meteodenik.model.Mesto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object MestaZdroj {
    // Храним список внутри (скрыто)
    private val _mesta = MutableStateFlow(
        listOf(
            Mesto(id = 1, nazev = "Zlín", teplotaC = 21.4),
            Mesto(id = 2, nazev = "Praha", teplotaC = 19.5),
            Mesto(id = 3, nazev = "Brno", teplotaC = null)
        )
    )

    // Наружу отдаем только для чтения
    val mesta: StateFlow<List<Mesto>> = _mesta.asStateFlow()

    fun pridej(nazev: String) {
        _mesta.update { soucasnaMesta ->
            val noveId = (soucasnaMesta.maxOfOrNull { it.id } ?: 0) + 1
            soucasnaMesta + Mesto(id = noveId, nazev = nazev.trim(), teplotaC = null)
        }
    }

    fun podleId(id: Int): Mesto? {
        return _mesta.value.find { it.id == id }
    }
}