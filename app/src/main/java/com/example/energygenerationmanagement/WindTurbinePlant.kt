package com.example.energygenerationmanagement

class WindTurbinePlant(
    name: String,
    maxPower: Double,
    efficiency: Double,
    var windSpeed: Double
) : PowerPlant(name, maxPower, efficiency) {

    override fun generatePower(): Double {
        return if (isRunning) maxPower * efficiency * (windSpeed / 25.0) else 0.0
    }

    fun updateWindSpeed(speed: Double) {
        windSpeed = speed
    }

    override fun maintenance() {
        efficiency = (efficiency + 0.03).coerceAtMost(1.0)
    }
}
