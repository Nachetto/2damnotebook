package com.example.nachorestaurante.framework.pantallamain

import com.example.nachorestaurante.domain.modelo.Customer

data class MainState (
    val customers: List<Customer> = emptyList(),
    val customersSeleccionadas: List<Customer> = emptyList(),
    val selectMode: Boolean = false,
    val error: String? = null
)