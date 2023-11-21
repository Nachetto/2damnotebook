package com.example.nachorestaurante.data.sources.service

import com.example.nachorestaurante.data.model.OrderResponse
import com.example.nachorestaurante.domain.modelo.Order
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Esta interfaz define los métodos que se utilizarán para hacer las peticiones a la API.
interface OrderService {
    @GET("orders")
    suspend fun getOrders(): Response<List<OrderResponse>>

    @DELETE("orders/{id}")
    suspend fun deleteOrder(@Path("id") id: Int): Response<ResponseBody>

    @POST("orders")
    suspend fun createOrder(order: Order): Response<String>
}