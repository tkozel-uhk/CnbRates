package cz.uhk.cnbrates

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.magnifier
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.wear.compose.material.dialog.Dialog
import cz.uhk.cnbrates.ui.theme.CNBRatesTheme
import java.nio.file.WatchEvent
import java.time.LocalDate
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CNBRatesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DateChooser()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DateChooser(modifier: Modifier = Modifier) {
    val datePickerState = rememberDatePickerState(System.currentTimeMillis())
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.inverseSurface,
                    titleContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                ),
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                })
        }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            DateSelector(datePickerState)
            Button(
                onClick = {
                    val selectedDate = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                    showCurrenciesList(selectedDate, context)
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 12.dp)
            ) {
                Text(text = "Read list")
            }
        }
    }
}

fun showCurrenciesList(selectedDate: Long, context: Context) {
    val date = LocalDate.ofEpochDay(selectedDate / 1000 / 60 / 60 / 24)
    val intent = Intent(context, CurrencyListActivity::class.java)
    intent.putExtra("date", date.toString())
    context.startActivity(intent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelector(datePickerState: DatePickerState) {
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier.padding(8.dp)
    )
    {
        Column {
            Text(
                stringResource(R.string.date_select_desc),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(4.dp))
            DatePicker(
                datePickerState
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CNBRatesTheme {
        DateChooser()
    }
}