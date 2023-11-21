package com.example.nachorestaurante.data.repositorios
import com.example.nachorestaurante.data.model.toCustomer
import com.example.nachorestaurante.data.sources.service.CustomerService
import com.example.nachorestaurante.domain.modelo.Customer
import com.example.nachorestaurante.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
@ActivityRetainedScoped
class CustomerRepository @Inject constructor(private val customerService: CustomerService) {
    //lo llama el usecase, el cual lo llama el viewmodel, esto llama a la interfaz que hace un Response

    //metodo para sacar todos los customers
    suspend fun getCustomers(): NetworkResult<List<Customer>> {
        var l: List<Customer> = emptyList()
        customerService.getCustomers().body()?.let {
            l = it.map { customerResponse ->
                customerResponse.toCustomer()
            }
        }
        return NetworkResult.Success(l)
    }

    //metodo para borrar un customer
    suspend fun deleteCustomer(id: Int): NetworkResult<String> {
        return try {
            val response = customerService.deleteCustomer(id)
            if (response.isSuccessful) {
                // Utilizar el cuerpo de la respuesta directamente como un String
                val responseBodyString = response.body()?.string() ?: "Deleted successfully"
                NetworkResult.Success(responseBodyString)
            } else {
                // Intentar obtener el mensaje del cuerpo de error si existe
                val errorBodyString = response.errorBody()?.string() ?: "Unknown error"
                NetworkResult.Error(errorBodyString)
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "An error occurred")
        }
    }



    suspend fun getCustomerFromId(customerId: Int): NetworkResult<Customer> {
        return try {
            val response = customerService.getCustomer(customerId)
            if (response.isSuccessful) {
                // Asegúrate de que la respuesta es un OrderResponse antes de llamar a toOrder
                response.body()?.let { customerResponse ->
                    NetworkResult.Success(customerResponse.toCustomer())
                } ?: NetworkResult.Error("No customer found")
            } else {
                // Intentar obtener el mensaje del cuerpo de error si existe
                val errorBodyString = response.errorBody()?.string() ?: "Unknown error"
                NetworkResult.Error(errorBodyString)
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "An error occurred")
        }
    }



}