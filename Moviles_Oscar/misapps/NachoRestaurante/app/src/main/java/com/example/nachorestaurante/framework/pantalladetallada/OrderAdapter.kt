package com.example.nachorestaurante.framework.pantalladetallada

import com.example.nachorestaurante.framework.pantallamain.SwipeGesture
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nachorestaurante.R
import com.example.nachorestaurante.databinding.ViewOrderBinding
import com.example.nachorestaurante.domain.modelo.Order

class OrderAdapter(
    val context: Context,
    val actions: OrderActions
) :
    ListAdapter<Order, OrderAdapter.ItemViewholder>(DiffCallback()) {

    interface OrderActions {
        fun onDelete(order: Order)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_order, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    inner class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ViewOrderBinding.bind(itemView)

        fun bind(item: Order) {
            with(binding) {
                tvId.text = "id: "+item.id.toString()
                tvFecha.text = "fecha: "+item.orderDate.toString()
                tvTableId.text = "mesa: "+item.tableId.toString()
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }

    val swipeGesture = object : SwipeGesture(context) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val order = getItem(position)
                actions.onDelete(order)
            }
        }
    }
}


