package com.example.nachorestaurante.data.repositorios

import com.example.nachorestaurante.data.model.OrderResponse
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

    suspend fun deleteOrder(id: Int): NetworkResult<String> {

        return try {
            val response = orderService.deleteOrder(id)
            if (response.isSuccessful) {
                val responseBodyString = response.body()?.string() ?: "Deleted successfully"
                NetworkResult.Success(responseBodyString)
            } else {
                val errorBodyString = response.errorBody()?.string() ?: "Unknown error"
                NetworkResult.Error(errorBodyString)
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun createOrder(order: Order): NetworkResult<Order> {
        return try {
            val orderResponse = order.toOrderResponse()
            val response = orderService.createOrder(orderResponse)
            if (response.isSuccessful) {
                response.body()?.let {
                    NetworkResult.Success(it.toOrder())
                } ?: NetworkResult.Error("No order returned")
            } else {
                val errorBodyString = response.errorBody()?.string() ?: "Unknown error"
                NetworkResult.Error(errorBodyString)
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "An error occurred")
        }
    }





    suspend fun filterOrders(customerid: Int): List<Order> {
        var l: List<Order> = getOrders().data ?: emptyList()
        l = l.filter { order ->
            order.customerId == customerid
        }
        return l
    }

}