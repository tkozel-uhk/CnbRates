package cz.uhk.cnbrates.model

import java.time.LocalDate

interface CurrencyLoader {
    fun loadCurrencies(date : LocalDate): List<Currency>
}