package cz.utb.meteodenik.cv04.prehled

// 2. Formátování popisu jako extension funkce nad Double
fun Double.jakoCelsia(): String = "$this C"

// 1. MestoJ → data class Mesto (bez otazníků, ručních getterů a setterů)
data class Mesto(val nazev: String, val teplota: Double) {
    fun popis(): String = "$nazev: ${teplota.jakoCelsia()}"
}

// 3. Trojice booleanů → sealed interface se čtyřmi stavy
sealed interface Stav {
    data object Nacita : Stav
    data object Chyba : Stav
    data object Prazdno : Stav
    data class Hotovo(val mesta: List<Mesto>) : Stav
}

// 6. Prehled dostane seznam měst v primárním konstruktoru jako List
class Prehled(private val mesta: List<Mesto>) {

    // Proměnná pro stav inicializovaná podle předaného seznamu
    val stav: Stav = if (mesta.isEmpty()) Stav.Prazdno else Stav.Hotovo(mesta)

    // 3. when bez větve else, plně využívá sealed interface
    fun textStavu(): String = when (stav) {
        is Stav.Nacita -> "Načítám…"
        is Stav.Chyba -> "Chyba"
        is Stav.Prazdno -> "Nic tu není"
        is Stav.Hotovo -> "Měst: ${stav.mesta.size}" // Smart cast z (stav as Stav.Hotovo)
    }

    // 4. nejteplejsi() → jedno volání z kolekcového API
    fun nejteplejsi(): Mesto? = mesta.maxByOrNull { it.teplota }

    fun vypis() {
        // 5. Nikde for (i in 0 until ...) a .get(i), použita moderní iterace
        for (mesto in mesta) {
            println(mesto.popis())
        }
        println(textStavu())

        val nej = nejteplejsi()
        if (nej != null) {
            // 2. Spojování řetězců přes string template (bez znaku +)
            println("Nejtepleji: ${nej.nazev} (${nej.teplota.jakoCelsia()})")
        }
    }
}

fun main() {
    // 6. Vytvoření instance jedním voláním v main
    val p = Prehled(
        listOf(
            Mesto("Zlín", 21.4),
            Mesto("Brno", 23.0),
            Mesto("Otrokovice", 20.9)
        )
    )
    p.vypis()
}