package cz.uhk.cnbrates.model

import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Implementace [CurrencyLoader] pro kurovni listek CNB
 */
class CnbCurrencyLoader : CurrencyLoader {

    /**
     * URL sluzby CNB - format CSV
     */
    val CNB_URL = "https://www.cnb.cz/cs/financni_trhy/devizovy_trh/kurzy_devizoveho_trhu/denni_kurz.txt"
    override fun loadCurrencies(date: LocalDate): List<Currency> {
        val dateStr = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(date)
        val url = URL("$CNB_URL?date=$dateStr")
        val text = url.readText()
        val lines = text.lines().subList(2, text.lines().size - 2) //preskocime hlavicky
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