package com.example.nachorestaurante.data.sources.service

import com.example.nachorestaurante.data.model.CustomerResponse
import retrofit2.Response
import retrofit2.http.GET


interface CustomerService {

    @GET("customer")
    suspend fun getCharacters(): Response<List<CustomerResponse>>
}