package com.example.myapplication.data

import com.example.myapplication.domain.modelo.Raton
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.InputStream
import java.lang.reflect.Type

class Repository (file: InputStream? = null){

    private val ratones = mutableListOf<Raton>()
    init {
        val ratonEjemplo1 = Raton(
            modelo = "Viper Ultimate",
            marca = "Razer",
            color = "Negro",
            peso = 100,
            DPI = 1600,
            id = 1,
            fechaFabricacion = "1/1/1"
        )

        val ratonEjemplo2 = Raton(
            modelo = "MX Master 3",
            marca = "Logitech",
            color = "Gris",
            peso = 150,
            DPI = 4000,
            id = 2,
            fechaFabricacion = "2/2/2"
        )

        val ratonEjemplo3 = Raton(
            modelo = "DeathAdder Elite",
            marca = "Razer",
            color = "Negro",
            peso = 95,
            DPI = 16000,
            id = 3,
            fechaFabricacion = "3/3/3"
        )

        val ratonEjemplo4 = Raton(
            modelo = "G Pro Wireless",
            marca = "Logitech",
            color = "Blanco",
            peso = 80,
            DPI = 16000,
            id = 4,
            fechaFabricacion = "4/4/4"
        )

        val ratonEjemplo5 = Raton(
            modelo = "Mamba Elite",
            marca = "Razer",
            color = "Negro",
            peso = 96,
            DPI = 16000,
            id = 5,
            fechaFabricacion = "5/5/5"
        )

        addRaton(ratonEjemplo1)
        addRaton(ratonEjemplo2)
        addRaton(ratonEjemplo3)
        addRaton(ratonEjemplo4)
        addRaton(ratonEjemplo5)
    }


    /*
    init {
        if (ratones.size == 0) {
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

            val listaTipo: Type = Types.newParameterizedType(
                MutableList::class.java, Raton::class.java
            )

            val listaRatones = file?.bufferedReader()?.readText()?.let { contenidoFichero ->
                moshi.adapter<List<Raton>>(listaTipo).fromJson(contenidoFichero)
            }
            ratones.addAll(listaRatones!!)

        }

    }
    */

    fun addRaton(raton: Raton):Boolean {
        return ratones.add(raton)
    }

    fun deleteRaton(raton:Raton):Boolean{
        return ratones.remove(raton)
    }

    fun modifyRaton(ratonId: Int, newRaton: Raton): Boolean {
        val existingRaton = ratones.find { it.id == ratonId }
        if (existingRaton != null) {
            existingRaton.modelo = newRaton.modelo
            existingRaton.marca = newRaton.marca
            existingRaton.color = newRaton.color
            existingRaton.peso = newRaton.peso
            existingRaton.DPI = newRaton.DPI
            existingRaton.fechaFabricacion = newRaton.fechaFabricacion
            return true
        }
        return false
    }


    fun getRatonById(id: Int): Raton? {
        return ratones.find { it.id == id }
    }
    fun getLastIdFromRatonesList(): Int? {
        if (ratones.isNotEmpty()) {
            val lastRaton = ratones.last()
            return lastRaton.id
        }
        return null
    }

    fun getAllRatones(): List<Raton> {
        return ratones

    }

    companion object {
        private var instance: Repository? = null

        fun getInstance(): Repository {
            if (instance == null) {
                instance = Repository()
            }
            return instance!!
        }
    }
}
