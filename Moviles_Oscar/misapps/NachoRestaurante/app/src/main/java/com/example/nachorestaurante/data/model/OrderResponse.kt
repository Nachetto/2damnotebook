package com.example.nachorestaurante.data.model

import com.example.nachorestaurante.data.common.Constants
import com.example.nachorestaurante.domain.modelo.Order
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class OrderResponse(
    @SerializedName(Constants.CUSTOMERID)
    val customerId: Int,

    @SerializedName(Constants.ORDERDATE)
    val orderDate: String,

    @SerializedName(Constants.ID)
    val orderId: Int,

    @SerializedName(Constants.TABLEID)
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