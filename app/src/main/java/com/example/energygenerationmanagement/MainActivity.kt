package com.example.energygenerationmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnergyApp()
        }
    }
}

@Composable
fun EnergyApp() {
    val solar = remember { SolarPlant("Сонячна станція", 100.0, 0.85, 8.0) }
    val wind = remember { WindTurbinePlant("Вітрова станція", 150.0, 0.9, 12.0) }
    val hydro = remember { HydroPlant("Гідроелектростанція", 200.0, 0.95, 80.0) }

    var solarInput by remember { mutableStateOf(solar.sunlightHours.toString()) }
    var windInput by remember { mutableStateOf(wind.windSpeed.toString()) }
    var hydroInput by remember { mutableStateOf(hydro.waterFlow.toString()) }

    var solarStatus by remember { mutableStateOf(solar.reportStatus()) }
    var windStatus by remember { mutableStateOf(wind.reportStatus()) }
    var hydroStatus by remember { mutableStateOf(hydro.reportStatus()) }

    val tabs = listOf("Сонце", "Вітер", "Вода")
    var selectedTab by remember { mutableStateOf(0) }

    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                "Система керування генерацією енергії",
                fontSize = 22.sp,
                modifier = Modifier.padding(16.dp)
            )

            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTab) {
                0 -> PlantCard(
                    plant = solar,
                    inputValue = solarInput,
                    onInputChange = { solarInput = it },
                    onAdjust = {
                        solar.adjustSunlight(solarInput.toDoubleOrNull() ?: 0.0)
                        solarStatus = solar.reportStatus()
                    },
                    statusText = solarStatus,
                    onStart = { solar.start(); solarStatus = solar.reportStatus() },
                    onStop = { solar.shutdown(); solarStatus = solar.reportStatus() },
                    onMaintenance = { solar.maintenance(); solarStatus = solar.reportStatus() },
                    inputLabel = "Години сонячного світла"
                )
                1 -> PlantCard(
                    plant = wind,
                    inputValue = windInput,
                    onInputChange = { windInput = it },
                    onAdjust = {
                        wind.updateWindSpeed(windInput.toDoubleOrNull() ?: 0.0)
                        windStatus = wind.reportStatus()
                    },
                    statusText = windStatus,
                    onStart = { wind.start(); windStatus = wind.reportStatus() },
                    onStop = { wind.shutdown(); windStatus = wind.reportStatus() },
                    onMaintenance = { wind.maintenance(); windStatus = wind.reportStatus() },
                    inputLabel = "Швидкість вітру (м/с)"
                )
                2 -> PlantCard(
                    plant = hydro,
                    inputValue = hydroInput,
                    onInputChange = { hydroInput = it },
                    onAdjust = {
                        hydro.adjustWaterFlow(hydroInput.toDoubleOrNull() ?: 0.0)
                        hydroStatus = hydro.reportStatus()
                    },
                    statusText = hydroStatus,
                    onStart = { hydro.start(); hydroStatus = hydro.reportStatus() },
                    onStop = { hydro.shutdown(); hydroStatus = hydro.reportStatus() },
                    onMaintenance = { hydro.maintenance(); hydroStatus = hydro.reportStatus() },
                    inputLabel = "Потік води (м/с)"
                )
            }
        }
    }
}


@Composable
fun PlantCard(
    plant: PowerPlant,
    inputValue: String,
    onInputChange: (String) -> Unit,
    onAdjust: () -> Unit,
    statusText: String,
    onStart: () -> Unit,
    onStop: () -> Unit,
    onMaintenance: () -> Unit,
    inputLabel: String
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(plant.name, fontSize = 20.sp)
            Text(statusText, fontSize = 16.sp)

            OutlinedTextField(
                value = inputValue,
                onValueChange = onInputChange,
                label = { Text(inputLabel) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onStart) { Text("Запустити") }
                Button(onClick = onStop) { Text("Зупинити") }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onAdjust) { Text("Змінити параметр") }
                Button(onClick = onMaintenance) { Text("Обслуговування") }
            }

            Text(
                "Поточна потужність: ${"%.2f".format(plant.generatePower())} МВт",
                fontSize = 16.sp
            )
        }
    }
}
