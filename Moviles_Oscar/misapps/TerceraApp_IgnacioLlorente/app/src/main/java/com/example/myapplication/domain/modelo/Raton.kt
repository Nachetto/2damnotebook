package com.example.myapplication.domain.modelo

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
class Raton(
    @Json(name = "modelo")
    var modelo: String,
    @Json(name = "marca")
    var marca: String,
    @Json(name = "color")
    var color: String,
    @Json(name = "peso")
    var peso: Int,
    @Json(name = "DPI")
    var DPI: Int,
    @Json(name = "id")
    var id: Int,
    @Json(name = "fechaFabricacion")
    var fechaFabricacion: String
) : Parcelable

