package cz.utb.meteodenik.model

data class Mesto(
    val id: Int,           // stálá identita – klíč pro seznam
    val nazev: String,
    val teplotaC: Double?  // null = teplotu zatím neznáme (API v lekci 11)
)