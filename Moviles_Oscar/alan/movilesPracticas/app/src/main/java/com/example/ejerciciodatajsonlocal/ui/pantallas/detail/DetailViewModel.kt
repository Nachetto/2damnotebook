package com.example.ejerciciodatajsonlocal.ui.pantallas.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ejerciciodatajsonlocal.domain.model.Pokemon
import com.example.ejerciciodatajsonlocal.domain.usecases.AddPokemonUseCase
import com.example.ejerciciodatajsonlocal.domain.usecases.DeletePokemonUseCase
import com.example.ejerciciodatajsonlocal.domain.usecases.GetNextIdPokemonUseCase
import com.example.ejerciciodatajsonlocal.domain.usecases.UpdatePokemonUseCase


class DetailViewModel(
    private val addPokemonUseCase: AddPokemonUseCase,
    private val getNextIdPokemonUseCase: GetNextIdPokemonUseCase,
    private val deletePokemonUseCase: DeletePokemonUseCase,
    private val updatePokemonUseCase: UpdatePokemonUseCase,
) : ViewModel() {

    private val _uiState = MutableLiveData<DetailState>()

    val uiState: LiveData<DetailState> get() = _uiState

    init {
        _uiState.value = DetailState()
    }


    fun errorShownAlready() {
        _uiState.value = _uiState.value?.copy(message = null)
    }

    fun addPokemon(
        name: String,
        experienciaBase: Int,
        altura: Int,
        peso: Int,
        imagen: String,
        tipoPokemon: String,
    ) {
        val pokemon = Pokemon(
            id = getNextIdPokemonUseCase(),
            nombre = name,
            experienciaBase = experienciaBase,
            altura = altura,
            peso = peso,
            imagen = imagen,
            tipoPokemon = tipoPokemon,
        )

        if (addPokemonUseCase(pokemon)) {
            _uiState.value = _uiState.value?.copy(
                message = "Pokemon añadido",
            )
        }
    }

    fun deletePokemon(pokemonId: Int) {
        if (deletePokemonUseCase(pokemonId)) {
            _uiState.value = _uiState.value?.copy(
                message = "Pokemon eliminado",
            )
        } else {
            _uiState.value = _uiState.value?.copy(
                message = "Pokemon NO eliminado!!!",
            )
        }
    }

    fun updatePokemon(
        pokemonId: Int,
        name: String,
        experienciaBase: Int,
        weight: Int,
        height: Int,
    ) {
        if (updatePokemonUseCase(pokemonId, name, experienciaBase, weight, height)) {
            _uiState.value = _uiState.value?.copy(
                message = "Pokemon actualizado",
            )
        } else {
            _uiState.value = _uiState.value?.copy(
                message = "Pokemon NO actualizado!!!",
            )
        }


    }


}


/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class DetailViewModelFactory(
    private val addPokemonUseCase: AddPokemonUseCase,
    private val getNextIdPokemonUseCase: GetNextIdPokemonUseCase,
    private val deletePokemonUseCase: DeletePokemonUseCase,
    private val updatePokemonUseCase: UpdatePokemonUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return DetailViewModel(
                addPokemonUseCase,
                getNextIdPokemonUseCase,
                deletePokemonUseCase,
                updatePokemonUseCase,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


