package cz.utb.meteodenik.data

enum class DuvodSelhani {
    BEZ_INTERNETU,
    TIMEOUT,
    SERVER,
    CHYBA_DAT,
    NEZNAMA
}

sealed interface VysledekSite<out T> {
    data class Uspech<T>(val data: T) : VysledekSite<T>
    data class Chyba(val duvod: DuvodSelhani) : VysledekSite<Nothing>
}