package com.example.myapplication.data

import com.example.myapplication.domain.modelo.Raton

class Repository {

    private val ratones = mutableListOf<Raton>()

    fun addRaton(raton: Raton):Boolean {
        return ratones.add(raton)
    }

    fun deleteRaton(raton:Raton):Boolean{
        return ratones.remove(raton)
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


    init {
        val ratonEjemplo = Raton(
            modelo = "Viper Ultimate",
            marca = "Razer",
            color = "Negro",
            peso = 100,
            DPI = 1600,
            id = 1,
            fechaFabricacion = "1/1/1"
        )
        addRaton(ratonEjemplo)
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
