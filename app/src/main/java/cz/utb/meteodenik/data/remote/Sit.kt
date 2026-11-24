package cz.utb.meteodenik.data.remote

import cz.utb.meteodenik.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object Sit {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val klient: OkHttpClient = OkHttpClient.Builder()
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BASIC
                    }
                )
            }
        }
        .build()

    private val konvertor = json.asConverterFactory("application/json; charset=UTF8".toMediaType())

    val meteoApi: MeteoApi = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .client(klient)
        .addConverterFactory(konvertor)
        .build()
        .create(MeteoApi::class.java)

    val geoApi: GeoApi = Retrofit.Builder()
        .baseUrl("https://geocoding-api.open-meteo.com/")
        .client(klient)
        .addConverterFactory(konvertor)
        .build()
        .create(GeoApi::class.java)
}