package cz.uhk.cnbrates.model

import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class CnbCurrencyLoader : CurrencyLoader {
    /*override fun loadCurrencies(): List<Currency> {
        return mutableListOf(
            Currency("Austrálie", "dolar", 1, "AUD", 17.22),
            Currency("Brazílie", "real", 1, "BRL", 4.09),
            Currency("Bulharsko", "lev", 1, "BGN", 13.03),
            Currency("Čína", "žen-min-pi", 1, "CNY", 3.43),
            Currency("Dánsko", "koruna", 1, "DKK", 3.47),
        )
    }*/

    val CNB_URL = "https://www.cnb.cz/cs/financni_trhy/devizovy_trh/kurzy_devizoveho_trhu/denni_kurz.txt"
    override fun loadCurrencies(date: LocalDate): List<Currency> {
        val dateStr = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(date)
        val url = URL("$CNB_URL?date=$dateStr")
        val text = url.readText()
        val lines = text.lines().subList(2, text.lines().size - 2)
        val currencies = mutableListOf<Currency>()
        for (line in lines) {
            val parts = line.split("|")
            if (parts.size < 5) continue
            val country = parts[0]
            val currency = parts[1]
            val amount = parts[2].toInt()
            val code = parts[3]
            val rate = parts[4].replace(",", ".").toDouble()
            currencies.add(Currency(country, currency, amount, code, rate))
        }
        return currencies
    }
}