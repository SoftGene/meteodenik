package cz.utb.meteodenik.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GeoOdpovedDto(
    val results: List<GeoMestoDto> = emptyList()
)

@Serializable
data class GeoMestoDto(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String? = null,
    val admin1: String? = null
)