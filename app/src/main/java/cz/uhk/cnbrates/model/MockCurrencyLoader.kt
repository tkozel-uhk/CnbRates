package cz.uhk.cnbrates.model

import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class MockCurrencyLoader : CurrencyLoader {
    override fun loadCurrencies(date: LocalDate): List<Currency> {
        return mutableListOf(
            Currency("Austrálie", "dolar", 1, "AUD", 17.22),
            Currency("Brazílie", "real", 1, "BRL", 4.09),
            Currency("Bulharsko", "lev", 1, "BGN", 13.03),
            Currency("Čína", "žen-min-pi", 1, "CNY", 3.43),
            Currency("Dánsko", "koruna", 1, "DKK", 3.47),
        )
    }

}