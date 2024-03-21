package cz.uhk.cnbrates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.uhk.cnbrates.model.CnbCurrencyLoader
import cz.uhk.cnbrates.model.Currency
import cz.uhk.cnbrates.model.MockCurrencyLoader
import cz.uhk.cnbrates.tools.loadFlags
import cz.uhk.cnbrates.ui.theme.CNBRatesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CurrencyListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val flags = loadFlags(this)
        val intent = intent
        val date: LocalDate =
            LocalDate.parse(intent.getStringExtra("date"), DateTimeFormatter.ISO_DATE)
        val scope = CoroutineScope(Dispatchers.Default)

        scope.launch {
            runBlocking {
                // Proveďte síťový požadavek
                val currencies = CnbCurrencyLoader().loadCurrencies(date)
                // Aktualizace UI s výsledkem
                withContext(Dispatchers.Main) {
                    setContent {
                        CNBRatesTheme {
                            // A surface container using the 'background' color from the theme
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                CurrencyList(currencies, flags)
                            }
                        }
                    }
                }
            }
        }


    }
}

@Composable
fun CurrencyList(currencies: List<Currency>, flags: Map<String, String>) {
    //run in background

    val currList = remember {
        currencies
    }
    LazyColumn {
        items(currList) {
            val flag = flags[it.code]
            CurrencyItem(it, flag ?: "")
        }
    }
}

val currencyFormater: NumberFormat = java.text.NumberFormat.getCurrencyInstance()

@Composable
fun CurrencyItem(item: Currency, flag: String) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(end = 8.dp)
            ) {
                Text(text = flag, fontSize = 28.sp)
            }
            Column(modifier = Modifier.weight(0.5f)) {
                Text(text = "${item.code} (${item.name})", fontWeight = FontWeight.Bold)
                Text(text = item.country)
            }
            Column(
                modifier = Modifier.weight(0.5f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${item.quantity} ${item.code}",
                    modifier = Modifier.padding(end = 5.dp)
                )
                Text(
                    text = currencyFormater.format(item.rate), modifier =
                    Modifier.padding(end = 5.dp),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    CNBRatesTheme {
        CurrencyList(
            MockCurrencyLoader().loadCurrencies(LocalDate.now()),
            mapOf(
                "AUD" to "\uD83C\uDDE6\uD83C\uDDFA",
                "BRL" to "\uD83C\uDDE7\uD83C\uDDF7",
                "BGN" to "\uD83C\uDDE7\uD83C\uDDEC"
            )
        )
    }
}