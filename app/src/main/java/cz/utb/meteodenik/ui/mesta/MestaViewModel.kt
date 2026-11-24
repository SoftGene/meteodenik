package cz.utb.meteodenik.ui.mesta

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.utb.meteodenik.data.DuvodSelhani
import cz.utb.meteodenik.data.MestaZdroj
import cz.utb.meteodenik.data.MeteoRepository
import cz.utb.meteodenik.data.Repozitare
import cz.utb.meteodenik.data.VysledekSite
import cz.utb.meteodenik.model.MeteoZaznam
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MestaViewModel @JvmOverloads constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repozitar: MeteoRepository = Repozitare.meteo
) : ViewModel() {

    private val VYBRANE_ID_KLIC = "vybrane_mesto_id"

    private val _uiState = MutableStateFlow(
        MestaUiState(
            mesta = MestaZdroj.mesta.value.map { MestoSPocasim(it) },
            vybraneMestoId = savedStateHandle.get<Int>(VYBRANE_ID_KLIC)
        )
    )
    val uiState: StateFlow<MestaUiState> = _uiState.asStateFlow()

    init {
        obnovit()
    }

    fun vyberMesto(id: Int) {
        savedStateHandle[VYBRANE_ID_KLIC] = id
        _uiState.update { it.copy(vybraneMestoId = id) }
    }

    fun obnovit() {
        viewModelScope.launch {
            _uiState.update { it.copy(nacitaSe = true, chyba = null) }

            val aktualniMesta = MestaZdroj.mesta.value
            val vysledky = aktualniMesta.map { mesto ->
                async { mesto.id to repozitar.nactiPocasi(mesto.sirka, mesto.delka) }
            }.awaitAll()

            var prvniChyba: DuvodSelhani? = null
            val novaMapaPocasi = mutableMapOf<Int, MeteoZaznam>()

            for ((id, vysledek) in vysledky) {
                when (vysledek) {
                    is VysledekSite.Uspech -> novaMapaPocasi[id] = vysledek.data
                    is VysledekSite.Chyba -> if (prvniChyba == null) prvniChyba = vysledek.duvod
                }
            }

            _uiState.update { stav ->
                val aktualizovana = aktualniMesta.map { mesto ->
                    val starePocasi = stav.mesta.find { it.mesto.id == mesto.id }?.pocasi
                    MestoSPocasim(
                        mesto = mesto,
                        pocasi = novaMapaPocasi[mesto.id] ?: starePocasi
                    )
                }
                stav.copy(
                    mesta = aktualizovana,
                    nacitaSe = false,
                    chyba = prvniChyba
                )
            }
        }
    }
}