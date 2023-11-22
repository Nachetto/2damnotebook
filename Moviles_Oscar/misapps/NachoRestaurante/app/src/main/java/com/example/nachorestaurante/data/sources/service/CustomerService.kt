package com.example.nachorestaurante.data.sources.service

import com.example.nachorestaurante.data.common.Constants
import com.example.nachorestaurante.data.model.CustomerResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface CustomerService {
    @GET(Constants.CUSTOMERS)
    suspend fun getCustomers(): Response<List<CustomerResponse>>
    @DELETE(Constants.CUSTOMERSID)
    suspend fun deleteCustomer(@Path("id") id: Int): Response<ResponseBody>
    @GET(Constants.CUSTOMERSID)
    suspend fun getCustomer(@Path("id") id: Int ): Response<CustomerResponse>


}