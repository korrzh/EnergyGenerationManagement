package com.example.energygenerationmanagement

class HydroPlant(
    name: String,
    maxPower: Double,
    efficiency: Double,
    var waterFlow: Double
) : PowerPlant(name, maxPower, efficiency) {

    override fun generatePower(): Double {
        return if (isRunning) maxPower * efficiency * (waterFlow / 100.0) else 0.0
    }

    fun adjustWaterFlow(flow: Double) {
        waterFlow = flow
    }

    override fun maintenance() {
        efficiency = (efficiency + 0.04).coerceAtMost(1.0)
    }
}

