package com.example.myapplication.data

import com.example.myapplication.domain.modelo.Raton
import java.util.Calendar

class Repository {

    private val ratones = mutableListOf<Raton>()

    fun addRaton(raton: Raton) {
        ratones.add(raton)
    }

    fun deleteRaton(raton:Raton){
        ratones.remove(raton)
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
            fechaFabricacion = Calendar.getInstance()
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
