package com.example.nachorestaurante.data.sources.service

import com.example.nachorestaurante.data.model.CustomerResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

// Esta interfaz define los métodos que se utilizarán para hacer las peticiones a la API.
interface CustomerService {
    @GET("customers")
    suspend fun getCustomers(): Response<List<CustomerResponse>>
    @DELETE("customers/{id}")
    suspend fun deleteCustomer(@Path("id") id: Int): Response<ResponseBody>
    @GET("customers/{id}")
    suspend fun getCustomer(@Path("id") id: Int ): Response<CustomerResponse>
}