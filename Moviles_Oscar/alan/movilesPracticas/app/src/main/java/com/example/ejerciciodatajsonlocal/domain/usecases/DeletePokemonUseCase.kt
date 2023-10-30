package com.example.ejerciciodatajsonlocal.domain.usecases

import com.example.ejerciciodatajsonlocal.data.Repository

class DeletePokemonUseCase(private val repository: Repository) {
    operator fun invoke(pokemonId : Int) = repository.deletePokemon(pokemonId)

}