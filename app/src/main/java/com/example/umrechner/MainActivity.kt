package com.example.umrechner

import android.R.attr.value
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import java.math.RoundingMode
import java.text.DecimalFormat

/* Lesen lohnt sich noch nicht: Alles noch Kladderadatsch
* Optionen 2 und 3 sind btw soweit nicht richtig implementiert */

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
//            TestDateConverter() // Geburtsdatum -> Alter in Minuten
//            TestMoneyConverter() // Cent in Jahre
        }
    }
}

@Composable
fun SqmConverter() {
    Column(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
    ) {
        var sqmErgebnis by remember { mutableStateOf("") }

        // Eingabe und Button für m² in Fußballfelder
        Row(modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
        ) {
            var text by remember { mutableStateOf("") }
            TextField(
                modifier = Modifier.weight(1.95f),
                value = text,
                onValueChange = { text = it},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // beschränkt die Eingabe auf Zahlen
                label = { Text(text = "m² in Fußballfelder") },
                placeholder ={ Text(text = "Eingeben") },
            )
            Spacer(modifier = Modifier.weight(0.1f))
            TestSqmButton(
                onClick = {},
                modifier = Modifier.weight(0.95f),
                wert = text
            )
        }
        // Ergebnis
        Text("Das sind $sqmErgebnis Fußballfelder")
    }


}

@Composable
fun TestSqmButton(onClick: () -> Unit, modifier: Modifier, wert: String) {
    val context = LocalContext.current
    OutlinedButton(onClick = {onClick()
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        val ergebnis = df.format(wert.toDouble() / 7140)
        Toast.makeText(
            context,
            "Etwa $ergebnis Fußballfelder",
            Toast.LENGTH_SHORT).show()
    },
    ) {
        Text("Ausrechnen")
    }
}

@Composable
fun SqmButton(onClick: () -> Unit, modifier: Modifier, wert: String) {
    OutlinedButton(onClick = {
        onClick()
        calculateSqm(wert)
    }
    ) {
        Text("Ausrechnen")
    }
}

fun calculateSqm(wert: String): String {
    val df = DecimalFormat ("#.##")
    df.roundingMode = RoundingMode.DOWN
    return df.format(wert.toDouble() / 7140)
}

@Composable
fun TestMoneyConverter() {
    TODO("Not yet implemented")
}

@Composable
fun TestDateConverter() {
    TODO("Not yet implemented")
}

//@Preview
//@Composable
//fun UnitConverterPreview(){
//    UnitConverter()
//}
//
//@Composable
//fun UnitConverter() {
//    var inputValue by remember { mutableStateOf("") }
//    var selectedConversion by remember { mutableStateOf("Konvertiere Optionen") }
//    var result by remember { mutableStateOf("") }
//    Column(modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally) {
//        Text("Umrechner-App")
//        OutlinedTextField(label= {Text ("Wert eintragen: qm, Alter, Cent")}, value = inputValue, onValueChange ={inputValue = it})
//        Row {
//            DropdownMenufun(selectedConversion= selectedConversion, onOptionSelected={selectedConversion=it})
//            SolveButton(onClick={result=convert(inputValue,selectedConversion)})
//            }
//        Text("Ergebnis: $result")
//        }
//    }
//
//@Composable
//fun DropdownMenufun(selectedConversion: String, onOptionSelected: (String) -> Unit){
//    var expanded by remember { mutableStateOf(false) }
//        Box{
//            Spacer(modifier = Modifier.width(16.dp))
//            Button(onClick = {expanded = !expanded}) {
//                Text("Auswahl")
//                Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
//            }
//            /*ständig fehler, wenn Nutzung DropdownMenuItem nach ILIAS pdf höa*/ // <- was bedeutet das
//            DropdownMenu(
//                expanded = expanded, onDismissRequest = {expanded = false})
//                {
//                val options = listOf("qm in Fußballfeldern","Alter in Minuten","Cent in Sekunden" )
//
//                    options.forEach{
//                        option ->
//                        DropdownMenuItem(
//                            text={Text(option)},
//                            onClick = {onOptionSelected(option)
//                                expanded = false}
//                        )
//                    }
//
//                }
//
//        }
//}
//
//
//@Composable
//fun SolveButton(onClick:() -> Unit){
//    Button(onClick=onClick){
//        Text("Ausrechnen!")
//    }
//}
//
///*Option 2: ist erstmal ohne Berechnung vom genauen Alter! Option 3: idfk wtf? 1ct = 1 sek*/
//fun convert(inputValue: String, selectedConversion: String): String {
//    val value = inputValue.toDouble()
//    return try {
//        when (selectedConversion) {
//            "qm in Fußballfeldern" -> "${value / 7140} Fußballfelder"
//            "Alter in Minuten" -> "${value * 525600} Minuten"
//            "Cent in Sekunden" -> "${value * 6000} Cent"
//            else -> "Bitte wähle eine Dropdown Option aus."
//        }
//    } catch (e: NumberFormatException) {
//        "Nicht gültig"
//        }
//    }
