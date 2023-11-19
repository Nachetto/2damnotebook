package com.example.nachorestaurante.data.repositorios

import com.example.nachorestaurante.data.model.toOrder
import com.example.nachorestaurante.data.sources.service.OrderService
import com.example.nachorestaurante.domain.modelo.Order
import com.example.nachorestaurante.utils.NetworkResult
import java.io.InputStream
import javax.inject.Inject

class OrderRepository  @Inject constructor(private val orderService: OrderService){
    //lo llama el usecase, el cual lo llama el viewmodel, esto llama a la interfaz que hace un Response

    //metodo para sacar todos los orders
    suspend fun getOrders(): NetworkResult<List<Order>> {
        var l: List<Order> = emptyList()
        orderService.getOrders().body()?.let {
            l = it.map { orderResponse ->
                orderResponse.toOrder()
            }
        }
        return NetworkResult.Success(l)
    }

    //metodo para borrar un order
    suspend fun deleteOrder(id: Int): NetworkResult<String> {
        var l: String = ""
        orderService.deleteOrder(id).body()?.let {
            l = it
        }
        return NetworkResult.Success(l)
    }

    //metodo para crear un order
    suspend fun createOrder(order: Order): NetworkResult<String> {
        var l: String = ""
        orderService.createOrder(order).body()?.let {
            l = it
        }
        return NetworkResult.Success(l)
    }
}