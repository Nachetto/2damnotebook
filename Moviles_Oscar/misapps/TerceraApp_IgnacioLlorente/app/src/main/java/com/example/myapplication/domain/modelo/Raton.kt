package com.example.myapplication.domain.modelo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Raton(
    //TODO(Cargar desde JSON, no lo hago poruqe lo de cambiar el constructor de Repository me da pereza)
    var modelo: String,
    var marca: String,
    var color: String,
    var peso: Int,
    var DPI: Int,
    var id: Int,
    var fechaFabricacion: String
) : Parcelable