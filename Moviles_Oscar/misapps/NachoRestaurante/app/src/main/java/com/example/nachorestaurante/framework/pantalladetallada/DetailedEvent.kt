package com.example.nachorestaurante.framework.pantalladetallada

import com.example.nachorestaurante.domain.modelo.Order

sealed class DetailedEvent {
    class DeleteOrdersSeleccionados() : DetailedEvent()
    class DeleteOrder(val pedido: Order) : DetailedEvent()
    class SeleccionaOrder(val pedido: Order) : DetailedEvent()
    class GetOrders(val id :Int) : DetailedEvent()
    object StartSelectMode: DetailedEvent()
    object ResetSelectMode: DetailedEvent()
    class obtainCustomers(val id: Int) : DetailedEvent()
}