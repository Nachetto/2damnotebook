package com.example.nachorestaurante.framework.pantalladetallada

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nachorestaurante.domain.modelo.Order
import com.example.nachorestaurante.domain.usecases.AddOrderUseCase
import com.example.nachorestaurante.domain.usecases.DeleteOrderUseCase
import com.example.nachorestaurante.domain.usecases.GetAllOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailedViewModel @Inject constructor(
    private val getAllOrdersUseCase: GetAllOrdersUseCase,
    private val deleteOrderUseCase: DeleteOrderUseCase,
    private val addorderusecase: AddOrderUseCase
) : ViewModel(){
    private val listaOrders = mutableListOf<Order>()
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var selectedOrders = mutableListOf<Order>()
    private val _uiState = MutableLiveData(DetailedState())
    val uiState: LiveData<DetailedState> get() = _uiState


}