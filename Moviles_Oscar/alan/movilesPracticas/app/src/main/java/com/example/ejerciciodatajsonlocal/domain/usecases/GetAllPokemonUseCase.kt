package com.example.ejerciciodatajsonlocal.domain.usecases

import com.example.ejerciciodatajsonlocal.data.Repository

class GetAllPokemonUseCase(private val repository: Repository) {
    operator fun invoke() = repository.getListAllPokemons()
}
