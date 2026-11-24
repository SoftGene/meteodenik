package cz.utb.meteodenik.data

import cz.utb.meteodenik.data.remote.GeoApi
import cz.utb.meteodenik.data.remote.MeteoApi
import cz.utb.meteodenik.data.remote.Sit
import cz.utb.meteodenik.data.remote.naModel
import cz.utb.meteodenik.model.MeteoZaznam
import cz.utb.meteodenik.model.NalezeneMesto

class MeteoRepositoryImpl(
    private val meteoApi: MeteoApi = Sit.meteoApi,
    private val geoApi: GeoApi = Sit.geoApi
) : MeteoRepository {

    override suspend fun nactiPocasi(sirka: Double, delka: Double): VysledekSite<MeteoZaznam> {
        return bezpecneVolani {
            meteoApi.aktualniPocasi(sirka, delka).naModel()
        }
    }

    override suspend fun hledejMesta(dotaz: String): VysledekSite<List<NalezeneMesto>> {
        return bezpecneVolani {
            geoApi.hledejMesta(dotaz).results.map { it.naModel() }
        }
    }
}

object Repozitare {
    val meteo: MeteoRepository = MeteoRepositoryImpl()
}