package com.example.nachorestaurante.framework.pantallamain

import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nachorestaurante.data.repositorios.CustomerRepository
import com.example.nachorestaurante.domain.modelo.Customer
import com.example.nachorestaurante.domain.usecases.DeleteCustomerUseCase
import com.example.nachorestaurante.domain.usecases.GetAllCustomersUseCase
import com.example.nachorestaurante.framework.pantalladetallada.DetailedActivity
import com.example.nachorestaurante.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllCustomersUseCase: GetAllCustomersUseCase,
    private val deleteCustomerUseCase: DeleteCustomerUseCase,
) : ViewModel() {
    private val listaPersonas = mutableListOf<Customer>()
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var selectedPersonas = mutableListOf<Customer>()
    private val _uiState = MutableLiveData(MainState())
    val uiState: LiveData<MainState> get() = _uiState

    init {
        _uiState.value = MainState(
            personas = emptyList(),
            personasSeleccionadas = emptyList(),
            selectMode = false
        )
        getPersonas()
    }

    fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.SeleccionaPersona -> seleccionaPersona(event.persona)

            MainEvent.GetPersonas -> {
                getPersonas()
            }
            is MainEvent.GetPersonaFiltradas -> getPersonas(event.filtro)

            is MainEvent.DeletePersonasSeleccionadas -> {
                _uiState.value?.let {
                    deletePersona(it.personasSeleccionadas)
                    resetSelectMode()
                }
            }
            is MainEvent.DeletePersona -> {
                deletePersona(event.persona)
            }

            MainEvent.ResetSelectMode -> resetSelectMode()
            MainEvent.StartSelectMode -> _uiState.value =
                _uiState.value?.copy(selectMode = true)

        }
    }

    private fun seleccionaPersona(persona: Customer) {
        if (isSelected(persona)) {
            selectedPersonas.remove(persona)
        } else {
            selectedPersonas.add(persona)
        }
        _uiState.value = _uiState.value?.copy(personasSeleccionadas = selectedPersonas)
    }

    private fun getPersonas() {
        viewModelScope.launch {
            val result = getAllCustomersUseCase.invoke()
            when (result) {
                is NetworkResult.Error<*> -> _error.value = result.message ?: "mal"
                is NetworkResult.Loading<*> -> TODO()
                is NetworkResult.Success<*> -> {
                    // Asegúrate de que los datos son de tipo List<Customer>
                    if (result.data is List<*>) {
                        listaPersonas.clear()
                        listaPersonas.addAll(result.data as Collection<Customer>)
                    }
                }
            }
            _uiState.value = _uiState.value?.copy(personas = listaPersonas)
        }
    }
    private fun getPersonas(filtro: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(
                personas = listaPersonas.filter { persona ->
                    persona.name.contains(filtro, ignoreCase = true)
                }.toList()
            )
        }
    }


    private fun deletePersona(personas: List<Customer>) {
        viewModelScope.launch {
            // Hacemos una copia de la lista original para iterar sobre ella
            val copiaPersonas = personas.toList()

            // Lista para rastrear los elementos que se eliminarán.
            val personasParaEliminar = mutableListOf<Customer>()

            // Bucle que intenta borrar cada persona de la copia y si hay error, rompe el bucle.
            var isSuccessful = true
            for (persona in copiaPersonas) {
                val result = deleteCustomerUseCase.invoke(persona)
                if (result is NetworkResult.Error<*>) {
                    _error.value = "Error al borrar"
                    isSuccessful = false
                    break // Sale del bucle si hay un error.
                } else {
                    personasParaEliminar.add(persona) // Agrega a la lista temporal si el borrado fue exitoso.
                }
            }

            // Si todas las personas se borraron exitosamente, actualiza la lista original.
            if (isSuccessful) {
                listaPersonas.removeAll(personasParaEliminar)
                selectedPersonas.removeAll(personasParaEliminar)
                _uiState.value =
                    _uiState.value?.copy(personasSeleccionadas = selectedPersonas.toList())
            }

            // Vuelve a cargar la lista de personas, independientemente del resultado del borrado.
            getPersonas()
        }
    }


    private fun deletePersona(persona: Customer) {
        viewModelScope.launch {
            if (deleteCustomerUseCase.invoke(persona) is NetworkResult.Error<*>) {
                _error.value = "Error al borrar"
            } else {
                listaPersonas.remove(persona)
                selectedPersonas.remove(persona)
                _uiState.value =
                    _uiState.value?.copy(personasSeleccionadas = selectedPersonas.toList())
            }
        }
    }

    private fun resetSelectMode() {
        selectedPersonas.clear()
        _uiState.value =
            _uiState.value?.copy(selectMode = false, personasSeleccionadas = selectedPersonas)
    }

    private fun isSelected(persona: Customer): Boolean {
        return selectedPersonas.contains(persona)
    }
}