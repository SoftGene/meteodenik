package cz.utb.meteodenik.model

enum class StavPocasi {
    JASNO,
    POLOJASNO,
    ZATAZENO,
    MLHA,
    DEST,
    SNIH,
    BOURKA,
    NEZNAMY
}

data class MeteoZaznam(
    val teplotaC: Double,
    val vlhkostProcent: Int,
    val rychlostVetruKmh: Double,
    val stav: StavPocasi
)