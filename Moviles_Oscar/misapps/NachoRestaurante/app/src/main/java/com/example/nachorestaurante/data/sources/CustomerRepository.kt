package com.example.nachorestaurante.data.sources

import com.example.nachorestaurante.domain.modelo.Customer
import com.example.restaurantapi.utils.NetworkResultt

class CustomerRepository {
    //lo llama el usecase, el cual lo llama el viewmodel, esto llama a la interfaz que hace un Response
    suspend fun getCustomers(): NetworkResultt<List<Customer>> {
        var l: List<Customer> = emptyList()
        customerService.getCharacters().body()?.let {
            l = it.map { customerResponse ->
                customerResponse.toCustomer()
            }
        }
        return NetworkResultt.Success(l)
    }

}