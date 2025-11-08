package com.example.energygenerationmanagement

class SolarPlant(
    name: String,
    maxPower: Double,
    efficiency: Double,
    var sunlightHours: Double
) : PowerPlant(name, maxPower, efficiency) {

    override fun generatePower(): Double {
        return if (isRunning) maxPower * efficiency * (sunlightHours / 24.0) else 0.0
    }

    fun adjustSunlight(hours: Double) {
        sunlightHours = hours
    }

    override fun maintenance() {
        // після обслуговування трохи підвищується ефективність
        efficiency = (efficiency + 0.05).coerceAtMost(1.0)
    }
}
