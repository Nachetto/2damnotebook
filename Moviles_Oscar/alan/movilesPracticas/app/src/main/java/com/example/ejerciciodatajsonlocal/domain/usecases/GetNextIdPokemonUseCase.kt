package com.example.ejerciciodatajsonlocal.domain.usecases

import com.example.ejerciciodatajsonlocal.data.Repository

class GetNextIdPokemonUseCase(private val repository: Repository) {
    operator fun invoke() = repository.getNextIdPokemon()
}