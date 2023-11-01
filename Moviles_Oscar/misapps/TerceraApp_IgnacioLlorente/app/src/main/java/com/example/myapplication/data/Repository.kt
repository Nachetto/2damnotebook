package com.example.myapplication.data

import com.example.myapplication.domain.modelo.Raton
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.InputStream
import java.lang.reflect.Type

class Repository (file: InputStream? = null) {

    private var ratones: MutableList<Raton> = mutableListOf()
    init {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

        val listaTipo: Type = Types.newParameterizedType(
            MutableList::class.java, Raton::class.java
        )

        val listaRatones = file?.bufferedReader()?.readText()?.let { contenidoFichero ->
            moshi.adapter<List<Raton>>(listaTipo).fromJson(contenidoFichero)
        }
        if (listaRatones != null) {
            ratones.addAll(listaRatones)
        }
    }

    fun addRaton(raton: Raton): Boolean {
        return ratones.add(raton)
    }

    fun deleteRaton(raton: Raton): Boolean {
        return ratones.remove(raton)
    }

    fun modifyRaton(ratonId: Int, newRaton: Raton): Boolean {
        println("ratones: "+ratones)
        val index = ratones.indexOfFirst { it.id == ratonId }
        if (index != -1) {
            val updatedRatones = ratones.toMutableList()
            val existingRaton = updatedRatones[index]
            val modifiedRaton = Raton(
                newRaton.modelo,
                newRaton.marca,
                newRaton.color,
                newRaton.peso,
                newRaton.DPI,
                existingRaton.id,
                newRaton.fechaFabricacion
            )
            updatedRatones[index] = modifiedRaton
            ratones = updatedRatones
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

        fun getInstance(file: InputStream? = null): Repository {
            if (instance == null) {
                instance = Repository(file)
            }
            return instance!!
        }
    }
}
