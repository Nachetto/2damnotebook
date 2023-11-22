package com.example.nachorestaurante.data.model

import com.example.nachorestaurante.domain.modelo.Order
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class OrderResponse(
    @SerializedName("customerId")
    val customerId: Int,

    @SerializedName("orderDate")
    val orderDate: String,

    @SerializedName("id")
    val orderId: Int,

    @SerializedName("tableId")
    val tableId: Int
)
fun OrderResponse.toOrder(): Order {
    return Order(
        customerId = customerId,
        orderDate = LocalDate.parse(orderDate),
        id = orderId,
        tableId = tableId
    )
}