package cz.utb.meteodenik.data

import cz.utb.meteodenik.model.MeteoZaznam
import cz.utb.meteodenik.model.NalezeneMesto

interface MeteoRepository {
    suspend fun nactiPocasi(sirka: Double, delka: Double): VysledekSite<MeteoZaznam>
    suspend fun hledejMesta(dotaz: String): VysledekSite<List<NalezeneMesto>>
}