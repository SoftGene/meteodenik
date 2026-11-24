package cz.utb.meteodenik.data.remote

import cz.utb.meteodenik.data.remote.dto.GeoOdpovedDto
import cz.utb.meteodenik.data.remote.dto.PredpovedDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MeteoApi {
    @GET("v1/forecast")
    suspend fun aktualniPocasi(
        @Query("latitude") sirka: Double,
        @Query("longitude") delka: Double,
        @Query("current") current: String = "temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m",
        @Query("timezone") timezone: String = "auto"
    ): PredpovedDto
}

interface GeoApi {
    @GET("v1/search")
    suspend fun hledejMesta(
        @Query("name") dotaz: String,
        @Query("count") pocet: Int = 5,
        @Query("language") jazyk: String = "cs"
    ): GeoOdpovedDto
}