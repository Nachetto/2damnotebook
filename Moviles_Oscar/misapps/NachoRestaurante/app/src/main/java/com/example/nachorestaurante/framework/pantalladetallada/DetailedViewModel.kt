package com.example.nachorestaurante.framework.pantalladetallada

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nachorestaurante.domain.usecases.AddOrderUseCase
import com.example.nachorestaurante.domain.usecases.GetAllFilteredOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.nachorestaurante.domain.modelo.Order
import com.example.nachorestaurante.domain.usecases.DeleteOrderUseCase
import com.example.nachorestaurante.domain.usecases.GetCustomerFromIdUseCase
import com.example.nachorestaurante.utils.NetworkResult
import kotlinx.coroutines.launch
import java.time.LocalDate


@HiltViewModel
class DetailedViewModel @Inject constructor(
    private val getAllFilteredOrdersUseCase: GetAllFilteredOrdersUseCase,
    private val deleteOrderUseCase: DeleteOrderUseCase,
    private val addorderusecase: AddOrderUseCase,
    private val getCustomerFromIdUseCase: GetCustomerFromIdUseCase
) : ViewModel() {
    private val listaOrders = mutableListOf<Order>()
    private val _error = MutableLiveData<String>()
    private val _uiState = MutableLiveData(DetailedState())
    val uiState: LiveData<DetailedState> get() = _uiState

    fun handleEvent(event: DetailedEvent) {
        when (event) {
            is DetailedEvent.GetOrders -> {
                getOrders(event.id)
            }

            is DetailedEvent.DeleteOrder -> {
                deleteOrder(event.pedido)
            }

            is DetailedEvent.GetCustomer -> getCustomer(event.id)
            is DetailedEvent.AddOrder -> addOrder(event.tableid)
        }
    }

    private fun addOrder(tableid: Int) {
        viewModelScope.launch {
            val order = Order(0, _uiState.value?.persona?.id?: 0, LocalDate.now(), tableid)

            if (addorderusecase.invoke(order) is NetworkResult.Error<*> || order.customerId == 0) {
                _error.value = "Error al añadir"
            } else {
                listaOrders.add(order)
                _uiState.value = _uiState.value?.copy(orders = listaOrders)
            }
        }
    }

    private fun getCustomer(id: Int) {
        viewModelScope.launch {
            val result = getCustomerFromIdUseCase.invoke(id)
            _uiState.value= _uiState.value?.copy(persona = result)
        }
    }

    private fun getOrders(id: Int) {
        viewModelScope.launch {
            listaOrders.clear()
            listaOrders.addAll(getAllFilteredOrdersUseCase.invoke(id))
            _uiState.value = _uiState.value?.copy(orders = listaOrders)
        }
    }

    private fun deleteOrder(order: Order) {
        viewModelScope.launch {

            if (deleteOrderUseCase.invoke(order) is NetworkResult.Error<*>) {
                _error.value = "Error al borrar"
            } else {
                listaOrders.remove(order)
                _uiState.value =
                    _uiState.value?.copy(orders = listaOrders)
            }
        }
    }
}