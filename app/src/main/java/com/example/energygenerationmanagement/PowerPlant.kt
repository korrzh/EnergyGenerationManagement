package com.example.energygenerationmanagement

abstract class PowerPlant(
    val name: String,
    val maxPower: Double,
    var efficiency: Double
) {
    var isRunning: Boolean = false

    abstract fun generatePower(): Double

    open fun start() {
        isRunning = true
    }

    open fun shutdown() {
        isRunning = false
    }

    open fun maintenance() {
    }

    open fun reportStatus(): String {
        val status = if (isRunning) "Працює" else "Зупинена"
        return "$name | Статус: $status | Потужність: ${"%.2f".format(generatePower())} МВт | Ефективність: ${(efficiency * 100).toInt()}%"
    }
}
