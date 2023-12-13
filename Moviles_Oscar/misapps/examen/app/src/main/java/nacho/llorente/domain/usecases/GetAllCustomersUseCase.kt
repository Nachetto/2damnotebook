package com.example.nachorestaurante.domain.usecases

import com.example.nachorestaurante.data.repositorios.CustomerRepository
import com.example.nachorestaurante.domain.modelo.Customer
import com.example.nachorestaurante.utils.NetworkResult
import javax.inject.Inject

class GetAllCustomersUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
) {
    suspend operator fun invoke(): NetworkResult<List<Customer>> {
        return customerRepository.getCustomers()
    }
}