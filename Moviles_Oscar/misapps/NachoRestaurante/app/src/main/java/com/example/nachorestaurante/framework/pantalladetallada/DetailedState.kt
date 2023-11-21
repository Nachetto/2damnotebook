package com.example.nachorestaurante.framework.pantalladetallada

import com.example.nachorestaurante.domain.modelo.Customer
import com.example.nachorestaurante.domain.modelo.Order

data class DetailedState (
    val orders: List<Order> = emptyList(),
    val ordersSeleccionados: List<Order> = emptyList(),
    val persona: Customer? = null,
    val error: String? = null
)