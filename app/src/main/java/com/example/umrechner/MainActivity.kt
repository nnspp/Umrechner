package com.example.umrechner

import android.R.attr.duration
import android.R.attr.onClick
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UmrechnerStart()
        }
    }
}

@Preview
@Composable
fun PreviewMessageCard() {
    UmrechnerStart()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UmrechnerStart() {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Umrechner")
                }
            )
        }
    ) {
        innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            SqmConverter() // Quadratmeter in Fußballfelder
            MoneyConverter() // Cent in Jahre
            TestDateConverter() // Geburtsdatum -> Alter in Minuten

        }
    }
}

// hätte es gerne in mehrere funktionen ausgelagert aber bin zu blöd dafür leider
// m² werden in Fußballfelder a 7140 m² umgerechnet
@Composable
fun SqmConverter() {
    Column(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
    ) {
        var tempFlaeche by remember { mutableStateOf("") } // input vom Eingabefeld mit dem gerechnet wird, nicht im UI angezeigt
        var shownFlaeche by remember { mutableStateOf("0") } // der im UI angezeigte input
        var sqmErgebnis by remember { mutableStateOf("0") } // das Ergebnis

        // Eingabe und Button für m² in Fußballfelder
        Row(modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier.weight(1.95f),
                value = tempFlaeche,
                onValueChange = { tempFlaeche = it},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // beschränkt die Eingabe auf Zahlen
                label = { Text(text = "m² in Fußballfelder") },
                placeholder ={ Text(text = "Eingeben") },
            )
            Spacer(modifier = Modifier.weight(0.1f))
            OutlinedButton(onClick = {
                // als sqmErgebnis ausgegeben wird ein string, der das ergebnis auf zwei dezimalstellen gerundet beinhaltet
                // @TODO stürzt ab wenn kein wert übergeben wird
                val df = DecimalFormat("#.##")
                df.roundingMode = RoundingMode.DOWN
                sqmErgebnis = df.format(tempFlaeche.toDouble() / 7140)

                shownFlaeche = tempFlaeche
            },
            ) {
                Text("Ausrechnen")
            }
        }
        // Ergebnis. shownText wird zusammen mit sqmErgebnis geupdated
        Text("$shownFlaeche m² sind etwa $sqmErgebnis Fußballfelder")
    }


}

// Die App fragt nach einem Geldbetrag und gibt Tage bzw. Jahre aus.
@Composable
fun MoneyConverter() {
    Column(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
    ) {
        var tempGeldString by remember {mutableStateOf("")} // input vom Eingabefeld, nicht im UI angezeigt
        var shownGeld by remember {mutableStateOf("0")} // der im UI angezeigte input
        var moneyErgebnis: Duration by remember {mutableStateOf(0.seconds)} // der input als duration wert in sekunden

        Row(modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier.weight(1.95f),
                value = tempGeldString,
                onValueChange = { tempGeldString = it},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // beschränkt die Eingabe auf Zahlen
                label = { Text(text = "Cent") },
                placeholder ={ Text(text = "Eingeben") },
            )
            Spacer(modifier = Modifier.weight(0.1f))
            OutlinedButton(onClick = {
                // @TODO stürzt ab wenn kein wert übergeben wird
                moneyErgebnis = tempGeldString.toInt().seconds

                shownGeld = tempGeldString
            }) {
                Text("Ausrechnen")
            }
        }
        // Ergebnis. shownGeld wird zusammen mit moneyErgebnis geupdated
        Text("$shownGeld Cent entsprechen $moneyErgebnis") // ist erstmal auf englisch
    }
}

// Die App fragt das Geburtsdatum ab, berechnet das aktuelle Alter und wie vielen Minuten das entspricht.
//noch ein neuer versuch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestDateConverter() {
    Column(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
    ) {
        var geburtsDatum by remember { mutableStateOf(LocalDate.now()) }
        var alterInMinuten: Duration by remember {mutableStateOf(0.minutes)}

        Row(modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
        ) {
            Button(
                onClick = {

                },
                modifier = Modifier.weight(1.95f)
            ) {
                Text(text = "Geburtsdatum auswählen (funktioniert nicht :( )")
            }

            Spacer(modifier = Modifier.weight(0.1f))
            OutlinedButton(onClick = {


            }) {
                Text("Ausrechnen")
            }
        }
        Text("Seit $geburtsDatum sind ${alterInMinuten.inWholeMinutes} Minuten vergangen.")
    }
}




