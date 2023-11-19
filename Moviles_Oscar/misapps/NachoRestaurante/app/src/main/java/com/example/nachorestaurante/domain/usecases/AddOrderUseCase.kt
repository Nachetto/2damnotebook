package com.example.nachorestaurante.domain.usecases

import com.example.nachorestaurante.data.repositorios.OrderRepository
import com.example.nachorestaurante.domain.modelo.Order
import com.example.nachorestaurante.utils.NetworkResult
import javax.inject.Inject

class AddOrderUseCase @Inject constructor(private val repository: OrderRepository) {
    suspend operator fun invoke(order: Order): NetworkResult<String> {
        return repository.createOrder(order)
    }
}