package com.example.nachorestaurante.domain.modelo

import java.time.LocalDate

data class Order (
    val id: Int,
    val customerId: Int,
    val orderDate: LocalDate,
    val tableId: Int,
    var isSelected: Boolean = false,
)