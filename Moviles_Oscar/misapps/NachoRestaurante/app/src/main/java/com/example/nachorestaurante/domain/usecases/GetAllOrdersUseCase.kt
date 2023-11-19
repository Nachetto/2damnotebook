package com.example.nachorestaurante.domain.usecases

import com.example.nachorestaurante.data.repositorios.OrderRepository
import com.example.nachorestaurante.domain.modelo.Order
import com.example.nachorestaurante.utils.NetworkResult
import javax.inject.Inject

class GetAllOrdersUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke() :NetworkResult<List<Order>>{
        return orderRepository.getOrders()
    }
}