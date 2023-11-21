package com.example.nachorestaurante.framework.pantalladetallada

import com.example.nachorestaurante.domain.modelo.Order

sealed class DetailedEvent {
    class DeleteOrdersSeleccionados() : DetailedEvent()
    class DeleteOrder(val pedido: Order) : DetailedEvent()
    class GetOrders(val id :Int) : DetailedEvent()
    class GetCustomer(val id :Int) : DetailedEvent()
}