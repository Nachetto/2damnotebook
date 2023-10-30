package com.example.ejerciciodatajsonlocal.domain.usecases

import com.example.ejerciciodatajsonlocal.data.Repository
import com.example.ejerciciodatajsonlocal.domain.model.Pokemon

class AddPokemonUseCase(private val repository: Repository) {

    operator fun invoke(pokemon: Pokemon) = repository.addPokemon(pokemon)
}