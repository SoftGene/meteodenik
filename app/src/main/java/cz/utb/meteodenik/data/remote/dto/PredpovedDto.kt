package cz.utb.meteodenik.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PredpovedDto(
    val current: AktualniPocasiDto
)

@Serializable
data class AktualniPocasiDto(
    @SerialName("temperature_2m") val teplota: Double,
    @SerialName("relative_humidity_2m") val vlhkost: Int,
    @SerialName("weather_code") val kodPocasi: Int,
    @SerialName("wind_speed_10m") val rychlostVetru: Double
)