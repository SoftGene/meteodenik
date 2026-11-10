package cz.utb.meteodenik.ui.mesta

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.utb.meteodenik.data.MestaZdroj
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MestaViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Ключ для сохранения выбранного города в Bundle
    private val VYBRANE_ID_KLIC = "vybrane_mesto_id"

    val uiState: StateFlow<MestaUiState> = MestaZdroj.mesta.map { seznamMest ->
        MestaUiState(
            mesta = seznamMest,
            vybraneMestoId = savedStateHandle.get<Int>(VYBRANE_ID_KLIC)
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MestaUiState()
    )

    fun vyberMesto(id: Int) {
        savedStateHandle[VYBRANE_ID_KLIC] = id
    }

    fun pridejMesto(nazev: String) {
        MestaZdroj.pridej(nazev)
    }

    override fun onCleared() {
        super.onCleared()
    }
}