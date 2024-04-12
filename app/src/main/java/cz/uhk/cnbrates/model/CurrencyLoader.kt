package cz.uhk.cnbrates.model

import java.time.LocalDate

/**
 * Rozhraní pro nacítání dat o kurzech men
 */
interface CurrencyLoader {
    fun loadCurrencies(date : LocalDate): List<Currency>
}