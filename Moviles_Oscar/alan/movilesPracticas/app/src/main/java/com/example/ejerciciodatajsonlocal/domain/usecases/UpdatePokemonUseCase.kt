package com.example.ejerciciodatajsonlocal.domain.usecases

import com.example.ejerciciodatajsonlocal.data.Repository

class UpdatePokemonUseCase(private val repository: Repository) {

    operator fun invoke(pokemonId: Int, name: String,experienciaBase:Int, weight: Int, height: Int) =
        repository.updatePokemon(pokemonId, name, experienciaBase, weight, height)
}