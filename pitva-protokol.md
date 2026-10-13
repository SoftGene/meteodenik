| co jsem napsal                        | generovaný člen (přesný podpis v Javě) | kolik řádků bych psal v Javě ručně |
|:--------------------------------------| :--- | :--- |
| `data class Zapis(...)`               | `public final class Zapis` | ~ 50–60 řádků |
| `object Prevody`                      | `public final class Prevody` | ~ 15–20 řádků |
| `fun Double.zaokrouhlenoNaDesetiny()` | `public static final double zaokrouhlenoNaDesetiny(double $this$zaokrouhlenoNaDesetiny)` | ~ 3 řádky |
| `fun prumer(...)`                     | `public static final double prumer(@NotNull List zapisy, @NotNull Function1 vyber)` | ~ 10–15 řádků |
| `suspend fun nactiPoZdrzeni(...)`     | `public static final Object nactiPoZdrzeni(@NotNull List zapisy, @NotNull Continuation $completion)` | ~ 30–40 řádků |

1. Dekompilovaná třída Zapis má celkem 11 metod (nepočítaje 2 konstruktory). Jsou to: getMesto, getTeplota, getPoznamka, component1, component2, component3, copy, copy$default, toString, hashCode a equals. Z nich component1() až component3(), copy(), copy$default(), toString(), hashCode() a equals() nemají v původním kódu žádný náznak a kompilátor je vygeneroval automaticky na základě klíčového slova data.

2. Tento syntetický konstruktor slouží k podpoře výchozích hodnot parametrů (v kotlinovém kódu má parametr poznamka výchozí hodnotu ""). Parametr var5 typu int funguje jako bitová maska, pomocí které kompilátor za běhu pozná, jaké parametry byly při volání vynechány. Pokud byl parametr vynechán (v kódu if ((var5 & 4) != 0)), dosadí se do něj automaticky prázdný řetězec, a následně se zavolá hlavní konstruktor.

3. Prvním parametrem je samotná instance typu double (v dekompilátu pojmenovaná jako double $this$zaokrouhlenoNaDesetiny). Z toho plyne, že rozšiřující funkce (extension functions) v Kotlinu nejsou polymorfní (virtuální). JVM je překládá jako obyčejné statické metody. Rozhodnutí, jaká metoda se zavolá, je tedy pevně svázáno se statickým typem proměnné už při kompilaci, nikoliv se skutečným typem objektu za běhu.

4. Druhý parametr má typ @NotNull Function1 vyber. To prozrazuje, že JVM na své nejnižší úrovni nepodporuje předávání funkcí jako hodnot napřímo. Kotlin proto musí každou předávanou funkci nebo lambda výraz zabalit do objektu, který implementuje specifické rozhraní z Kotlin standardní knihovny (zde Function1), které obsahuje jedinou metodu invoke.

5. Návratový typ se změnil z očekávaného List<Zapis> na Object. Důvodem je, že takzvaná suspend funkce může vrátit buď samotný výsledek, nebo speciální konstantu COROUTINE_SUSPENDED, pokud zrovna na něco čeká (např. na delay(50)). Konstrukce switch ($continuation.label) se objevila proto, že Kotlin kompilátor transformoval funkci na stavový automat (state machine). Proměnná label si pamatuje aktuální stav, aby funkce věděla, do které větve kódu se má po uplynutí pauzy vrátit a kde přesně má pokračovat ve vykonávání.