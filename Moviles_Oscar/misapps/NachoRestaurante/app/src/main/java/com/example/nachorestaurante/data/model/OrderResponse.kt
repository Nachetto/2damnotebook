package com.example.nachorestaurante.data.model

import com.example.nachorestaurante.domain.modelo.Order
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

//recibe los datos de la api  y los convierte en un objeto de tipo Order
data class OrderResponse(
    @SerializedName("customerid")
    val customerId: Int,

    @SerializedName("orderdate")
    val orderDate: String,

    @SerializedName("orderid")
    val orderId: Int,

    @SerializedName("tableid")
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