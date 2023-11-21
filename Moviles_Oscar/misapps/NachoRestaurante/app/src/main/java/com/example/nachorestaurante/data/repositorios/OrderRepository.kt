package com.example.nachorestaurante.data.repositorios

import com.example.nachorestaurante.domain.modelo.Order
import com.example.nachorestaurante.utils.NetworkResult
import com.example.nachorestaurante.data.model.toOrder
import com.example.nachorestaurante.data.sources.service.OrderService
import javax.inject.Inject

class OrderRepository @Inject constructor(private val orderService: OrderService) {
    suspend private fun getOrders(): NetworkResult<List<Order>> {
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

        return try {
            val response = orderService.deleteOrder(id)
            if (response.isSuccessful) {
                // Utilizar el cuerpo de la respuesta directamente como un String
                val responseBodyString = response.body()?.string() ?: "Deleted successfully"
                NetworkResult.Success(responseBodyString)
            } else {
                // Intentar obtener el mensaje del cuerpo de error si existe
                val errorBodyString = response.errorBody()?.string() ?: "Unknown error"
                NetworkResult.Error(errorBodyString)
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "An error occurred")
        }
    }

    //metodo para crear un order
    suspend fun createOrder(order: Order): NetworkResult<String> {
        var l: String = ""
        orderService.createOrder(order).body()?.let {
            l = it
        }
        return NetworkResult.Success(l)
    }

    //metodo para filtrar todos los orders de un customerid especifico
    suspend fun filterOrders(customerid: Int): List<Order> {
        var l: List<Order> = getOrders().data ?: emptyList()
        l = l.filter { order ->
            order.customerId == customerid
        }
        return l
    }

}