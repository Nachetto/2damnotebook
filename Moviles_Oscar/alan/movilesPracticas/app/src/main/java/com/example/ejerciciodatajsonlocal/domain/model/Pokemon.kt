package com.example.ejerciciodatajsonlocal.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Pokemon(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val nombre: String,
    @Json(name = "base_experience")
    val experienciaBase: Int,
    @Json(name = "height")
    val altura: Int,
    @Json(name = "weight")
    val peso: Int,
    @Json(name = "sprites")
    val imagen: String,
    @Json(name = "types")
    val tipoPokemon: String,
) : Parcelable
