package cz.utb.meteodenik.ui.mesta

import cz.utb.meteodenik.model.Mesto

data class MestaUiState(
    val mesta: List<Mesto> = emptyList(),
    val vybraneMestoId: Int? = null,
    val nacteno: Boolean = false,
    val chyba: String? = null
)