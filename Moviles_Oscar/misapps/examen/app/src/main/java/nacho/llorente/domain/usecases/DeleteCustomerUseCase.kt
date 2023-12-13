package com.example.nachorestaurante.domain.usecases

import com.example.nachorestaurante.data.repositorios.CustomerRepository
import com.example.nachorestaurante.domain.modelo.Customer
import com.example.nachorestaurante.utils.NetworkResult
import javax.inject.Inject

class DeleteCustomerUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
)   {
    suspend operator fun invoke(customer: Customer):NetworkResult<String> {
        return customerRepository.deleteCustomer(customer.id)
    }
}