package cz.utb.meteodenik.data.remote

import cz.utb.meteodenik.data.remote.dto.GeoMestoDto
import cz.utb.meteodenik.data.remote.dto.PredpovedDto
import cz.utb.meteodenik.model.MeteoZaznam
import cz.utb.meteodenik.model.NalezeneMesto
import cz.utb.meteodenik.model.StavPocasi

fun PredpovedDto.naModel(): MeteoZaznam = MeteoZaznam(
    teplotaC = current.teplota,
    vlhkostProcent = current.vlhkost,
    rychlostVetruKmh = current.rychlostVetru,
    stav = kodNaStavPocasi(current.kodPocasi)
)

fun GeoMestoDto.naModel(): NalezeneMesto = NalezeneMesto(
    id = id,
    nazev = name,
    stat = country,
    sirka = latitude,
    delka = longitude
)

private fun kodNaStavPocasi(kod: Int): StavPocasi = when (kod) {
    0 -> StavPocasi.JASNO
    1, 2 -> StavPocasi.POLOJASNO
    3 -> StavPocasi.ZATAZENO
    45, 48 -> StavPocasi.MLHA
    in 51..67, in 80..82 -> StavPocasi.DEST
    in 71..77, 85, 86 -> StavPocasi.SNIH
    in 95..99 -> StavPocasi.BOURKA
    else -> StavPocasi.NEZNAMY
}