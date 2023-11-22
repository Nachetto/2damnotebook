package com.example.nachorestaurante.domain.modelo

import com.example.nachorestaurante.data.model.OrderResponse
import java.time.LocalDate

data class Order (
    val id: Int,
    val customerId: Int,
    val orderDate: LocalDate,
    val tableId: Int,
) {
    fun toOrderResponse(): OrderResponse {
        return OrderResponse(
            customerId = customerId,
            orderDate = orderDate.toString(),
            orderId = id,
            tableId = tableId
        )
    }
}