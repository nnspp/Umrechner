package com.example.umrechner

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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
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
//            DateConverter() // Geburtsdatum -> Alter in Minuten
            MoneyConverter() // Cent in Jahre
        }
    }
}

// hätte es gerne in mehrere funktionen ausgelagert aber bin zu blöd dafür leider
@Composable
fun SqmConverter() {
    Column(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
    ) {
        var tempFlaeche by remember { mutableStateOf("") } // input vom Eingabefeld
        var shownFlaeche by remember { mutableStateOf("0") } // der angezeigte input
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

// Die App fragt das Geburtsdatum ab, berechnet das aktuelle Alter und wie vielen Minuten das entspricht.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateConverter() {
    Column(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
    ) {
        var geburtsDatum by remember { mutableStateOf(LocalDate.now()) }
//        var formattedGeburtsDatum by remember {
//            derivedStateOf {
//                DateTimeFormatter
//                    .ofPattern("MMM dd yyyy")
//                    .format(geburtsDatum)
//            }
//        }
        var tesTest by remember {mutableStateOf("")}

        Row(modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
        ) {
            DatePicker(
                state = TODO(),
                modifier = TODO(),
                dateFormatter = TODO(),
                title = TODO(),
                headline = TODO(),
                showModeToggle = TODO(),
                colors = TODO()
            )
            Spacer(modifier = Modifier.weight(0.1f))
            OutlinedButton(onClick = {

            }) {
                Text("Ausrechnen")
            }
        }
        Text("Seit GEBURTSDATUM sind 000000 Minuten vergangen.")
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
        var tempGeld by remember { mutableStateOf("") }
        var shownGeld by remember {mutableStateOf("0")}
        var moneyErgebnis by remember { mutableStateOf("0") }

        Row(modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier.weight(1.95f),
                value = tempGeld,
                onValueChange = { tempGeld = it},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // beschränkt die Eingabe auf Zahlen
                label = { Text(text = "Geld") },
                placeholder ={ Text(text = "Eingeben") },
            )
            Spacer(modifier = Modifier.weight(0.1f))
            OutlinedButton(onClick = {
                shownGeld = tempGeld

            }) {
                Text("Ausrechnen")
            }
        }
        Text("$shownGeld € entspricht $moneyErgebnis Jahren")
    }
}

