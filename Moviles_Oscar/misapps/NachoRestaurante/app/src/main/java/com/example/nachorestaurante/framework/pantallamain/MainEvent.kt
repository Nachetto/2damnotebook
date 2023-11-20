package com.example.nachorestaurante.framework.pantallamain


import com.example.nachorestaurante.domain.modelo.Customer

sealed class MainEvent {
    class DeletePersonasSeleccionadas() : MainEvent()
    class DeletePersona(val persona:Customer) : MainEvent()
    class SeleccionaPersona(val persona: Customer) : MainEvent()
    class GetPersonaFiltradas(val filtro: String) : MainEvent()
    object GetPersonas : MainEvent()
    object StartSelectMode: MainEvent()
    object ResetSelectMode: MainEvent()
}
