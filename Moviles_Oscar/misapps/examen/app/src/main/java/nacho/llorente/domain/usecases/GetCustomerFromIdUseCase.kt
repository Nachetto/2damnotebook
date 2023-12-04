package com.example.nachorestaurante.domain.usecases

import com.example.nachorestaurante.data.repositorios.CustomerRepository
import com.example.nachorestaurante.domain.modelo.Customer
import java.time.LocalDate
import javax.inject.Inject

class GetCustomerFromIdUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
) {
    suspend operator fun invoke(customerid: Int): Customer {
        return customerRepository.getCustomerFromId(customerid).data?:
            Customer(0,"","","",0,LocalDate.of(0,0,0),false)
    }
}