package com.example.ejerciciodatajsonlocal.ui.pantallas.main

import com.example.ejerciciodatajsonlocal.domain.model.Pokemon

data class MainState (
    val pokemons: List<Pokemon> = emptyList()
)