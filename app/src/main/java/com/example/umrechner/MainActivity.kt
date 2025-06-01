package com.example.umrechner

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.umrechner.ui.theme.UmrechnerTheme

/* Lesen lohnt sich noch nicht: Alles noch Kladderadatsch
* Optionen 2 und 3 sind btw soweit nicht richtig implementiert */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UmrechnerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UnitConverter()
                }
            }
        }
    }
}

@Preview
@Composable
fun UnitConverterPreview(){
    UnitConverter()
}

@Composable
fun UnitConverter() {
    var inputValue by remember { mutableStateOf("") }
    var selectedConversion by remember { mutableStateOf("Konvertiere Optionen") }
    var result by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Umrechner-App")
        OutlinedTextField(label= {Text ("Wert eintragen: qm, Alter, Cent")}, value = inputValue, onValueChange ={inputValue = it})
        Row {
            DropdownMenufun(selectedConversion= selectedConversion, onOptionSelected={selectedConversion=it})
            SolveButton(onClick={result=convert(inputValue,selectedConversion)})
            }
        Text("Ergebnis: $result")
        }

    }

@Composable
fun DropdownMenufun(selectedConversion: String, onOptionSelected: (String) -> Unit){
    var expanded by remember { mutableStateOf(false) }
        Box{
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {expanded = !expanded}) {
                Text("Auswahl")
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
            }
            /*ständig fehler, wenn Nutzung DropdownMenuItem nach ILIAS pdf höa*/ // <- was bedeutet das
            DropdownMenu(
                expanded = expanded, onDismissRequest = {expanded = false})
                {
                val options = listOf("qm in Fußballfeldern","Alter in Minuten","Cent in Sekunden" )

                    options.forEach{
                        option ->
                        DropdownMenuItem(
                            text={Text(option)},
                            onClick = {onOptionSelected(option)
                                expanded = false}
                        )
                    }

                }

        }
}


@Composable
fun SolveButton(onClick:() -> Unit){
    Button(onClick=onClick){
        Text("Ausrechnen!")
    }
}

/*Option 2: ist erstmal ohne Berechnung vom genauen Alter! Option 3: idfk wtf? 1ct = 1 sek*/
fun convert(inputValue: String, selectedConversion: String): String {
    val value = inputValue.toDouble()
    return try {
        when (selectedConversion) {
            "qm in Fußballfeldern" -> "${value / 7140} Fußballfelder"
            "Alter in Minuten" -> "${value * 525600} Minuten"
            "Cent in Sekunden" -> "${value * 6000} Cent"
            else -> "Bitte wähle eine Dropdown Option aus."
        }
    } catch (e: NumberFormatException) {
        "Nicht gültig"
        }
    }
