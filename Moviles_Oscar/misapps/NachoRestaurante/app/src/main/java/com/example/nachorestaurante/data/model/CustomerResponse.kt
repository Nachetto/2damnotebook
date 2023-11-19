package com.example.nachorestaurante.data.model

import com.example.nachorestaurante.domain.modelo.Customer
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class CustomerResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("surname")
    val surname: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone")
    val phone: Int,

    @SerializedName("birthdate")
    val birthdate: String
)

fun CustomerResponse.toCustomer(): Customer {
    return Customer(
        id = id,
        name = name,
        surname = surname,
        email = email,
        phone = phone,
        birthdate = LocalDate.parse(birthdate)
    )
}
