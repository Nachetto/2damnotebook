package com.example.myapplication.domain.modelo

import java.util.Calendar

class Raton(
    val modelo: String,
    val marca: String,
    val color: String,
    val peso: Int,
    val DPI: Int,
    val id: Int,
    val fechaFabricacion: Calendar
)