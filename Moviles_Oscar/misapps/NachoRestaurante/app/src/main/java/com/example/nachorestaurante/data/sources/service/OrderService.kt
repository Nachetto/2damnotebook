package com.example.nachorestaurante.data.sources.service

import com.example.nachorestaurante.data.common.Constants
import com.example.nachorestaurante.data.model.OrderResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderService {
    @GET(Constants.ORDERS)
    suspend fun getOrders(): Response<List<OrderResponse>>

    @DELETE(Constants.ORDERSID)
    suspend fun deleteOrder(@Path(Constants.ID) id: Int): Response<ResponseBody>

    @POST("orders")
    suspend fun createOrder(@Body orderResponse: OrderResponse): Response<OrderResponse>

}