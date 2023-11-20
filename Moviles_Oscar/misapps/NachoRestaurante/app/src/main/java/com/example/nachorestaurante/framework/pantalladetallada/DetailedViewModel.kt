package com.example.nachorestaurante.framework.pantalladetallada

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nachorestaurante.domain.usecases.AddOrderUseCase
import com.example.nachorestaurante.domain.usecases.GetAllOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.nachorestaurante.domain.modelo.Order
import com.example.nachorestaurante.domain.usecases.DeleteOrderUseCase
import com.example.nachorestaurante.utils.NetworkResult
import kotlinx.coroutines.launch


@HiltViewModel
class DetailedViewModel @Inject constructor(
    private val getAllOrdersUseCase: GetAllOrdersUseCase,
    private val deleteOrderUseCase: DeleteOrderUseCase,
    private val addorderusecase: AddOrderUseCase
) : ViewModel() {
    private val listaOrders = mutableListOf<Order>()
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var selectedOrders = mutableListOf<Order>()
    private val _uiState = MutableLiveData(DetailedState())
    val uiState: LiveData<DetailedState> get() = _uiState

    init {
        _uiState.value = DetailedState(
            orders = emptyList(),
            ordersSeleccionados = emptyList(),
            selectMode = false
        )
    }

    fun handleEvent(event: DetailedEvent) {
        when (event) {
            is DetailedEvent.SeleccionaOrder -> seleccionaOrder(event.pedido)

            is DetailedEvent.GetOrders -> {
                getOrders(event.id)
            }

            is DetailedEvent.DeleteOrdersSeleccionados -> {
                _uiState.value?.let {
                    deleteOrder(it.ordersSeleccionados)
                    resetSelectMode()
                }
            }

            is DetailedEvent.DeleteOrder -> {
                deleteOrder(event.pedido)
            }

            DetailedEvent.ResetSelectMode -> resetSelectMode()
            DetailedEvent.StartSelectMode -> _uiState.value =
                _uiState.value?.copy(selectMode = true)

            is DetailedEvent.obtainCustomers -> obtainCustomers(event.id)
        }
    }

    private fun obtainCustomers(id: Int) {
        viewModelScope.launch {
            getAllOrdersUseCase.invoke(id)
        }
    }

    private fun seleccionaOrder(order: Order) {
        if (isSelected(order)) {
            selectedOrders.remove(order)
        } else {
            selectedOrders.add(order)
        }
        _uiState.value = _uiState.value?.copy(ordersSeleccionados = selectedOrders)
    }

    private fun getOrders(id: Int) {
        viewModelScope.launch {
            val result = getAllOrdersUseCase.invoke(id)
            when (result) {
                is NetworkResult.Error<*> -> _error.value = result.message ?: "mal"
                is NetworkResult.Loading<*> -> TODO()
                is NetworkResult.Success<*> -> {
                    // Asegúrate de que los datos son de tipo List<Order>
                    if (result.data is List<*>) {
                        listaOrders.clear()
                        listaOrders.addAll(result.data as Collection<Order>)
                    }
                }
            }
            _uiState.value = _uiState.value?.copy(orders = listaOrders)
        }
    }

    private fun deleteOrder(orders: List<Order>) {
        viewModelScope.launch {
            // Hacemos una copia de la lista original para iterar sobre ella
            val copiaOrders = orders.toList()

            // Lista para rastrear los elementos que se eliminarán.
            val ordersParaEliminar = mutableListOf<Order>()

            // Bucle que intenta borrar cada order de la copia y si hay error, rompe el bucle.
            var isSuccessful = true
            for (order in copiaOrders) {
                val result = deleteOrderUseCase.invoke(order)
                if (result is NetworkResult.Error<*>) {
                    _error.value = "Error al borrar"
                    isSuccessful = false
                    break // Sale del bucle si hay un error.
                } else {
                    ordersParaEliminar.add(order) // Agrega a la lista temporal si el borrado fue exitoso.
                }
            }

            // Si todas las orders se borraron exitosamente, actualiza la lista original.
            if (isSuccessful) {
                listaOrders.removeAll(ordersParaEliminar)
                selectedOrders.removeAll(ordersParaEliminar)
                _uiState.value =
                    _uiState.value?.copy(ordersSeleccionados = selectedOrders.toList())
            }

            // Vuelve a cargar la lista de orders, independientemente del resultado del borrado.
        }
    }


    private fun deleteOrder(order: Order) {
        viewModelScope.launch {
            if (deleteOrderUseCase.invoke(order) is NetworkResult.Error<*>) {
                _error.value = "Error al borrar"
            } else {
                listaOrders.remove(order)
                selectedOrders.remove(order)
                _uiState.value =
                    _uiState.value?.copy(ordersSeleccionados = selectedOrders.toList())
            }
        }
    }

    private fun resetSelectMode() {
        selectedOrders.clear()
        _uiState.value =
            _uiState.value?.copy(selectMode = false, ordersSeleccionados = selectedOrders)
    }

    private fun isSelected(order: Order): Boolean {
        return selectedOrders.contains(order)
    }

}