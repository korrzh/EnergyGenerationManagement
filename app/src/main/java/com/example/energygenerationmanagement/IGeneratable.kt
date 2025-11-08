package com.example.energygenerationmanagement

interface IGeneratable {
    fun generatePower(): Double
    fun shutdown()
    fun maintenance()
    fun adjustEfficiency(newEfficiency: Double)
    fun reportStatus(): String
}