package dam.nacho.data

import dam.nacho.domain.modelo.Raton

class Repository {

    private val ratones = mutableListOf<Raton>()

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
            existingRaton.dpi = newRaton.dpi
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


    init {
        val ratonEjemplo = Raton(
            modelo = "Viper Ultimate",
            marca = "Razer",
            color = "Negro",
            peso = 100,
            dpi = 1600,
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
