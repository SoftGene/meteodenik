package cz.utb.meteodenik.model

data class NalezeneMesto(
    val id: Int,
    val nazev: String,
    val stat: String?,
    val sirka: Double,
    val delka: Double
)