package cz.utb.meteodenik.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import cz.utb.meteodenik.data.MestaZdroj
import cz.utb.meteodenik.model.Mesto
import cz.utb.meteodenik.navigace.Detail

class DetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    // Получаем аргументы навигации
    private val destinace = savedStateHandle.toRoute<Detail>()

    // Ищем город в источнике данных по ID
    val mesto: Mesto? = MestaZdroj.podleId(destinace.mestoId)
}