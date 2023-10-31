package com.example.myapplication.ui.pantallaMain

import com.example.myapplication.domain.modelo.Raton


data class MainState(
    val ratones: List<Raton> = emptyList()
)