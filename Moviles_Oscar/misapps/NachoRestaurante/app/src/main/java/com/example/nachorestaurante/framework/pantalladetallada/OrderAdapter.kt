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
        fun onStartSelectMode(order: Order)
        fun itemHasClicked(order: Order)

    }

    private var selectedPersonas = mutableSetOf<Order>()

    fun startSelectMode() {
        selectedMode = true
        notifyDataSetChanged()
    }


    fun resetSelectMode() {
        selectedMode = false
        selectedPersonas.clear()
        notifyDataSetChanged()
    }

    fun setSelectedItems(personasSeleccionadas: List<Order>){
        selectedPersonas.clear()
        selectedPersonas.addAll(personasSeleccionadas)
    }

    private var selectedMode: Boolean = false

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

            itemView.setOnLongClickListener {
                if (!selectedMode) {
//                    selectedMode = true
                    actions.onStartSelectMode(item)
//                    item.isSelected = true
//                    binding.selected.isChecked = true
                    //selectedPersonas.add(item)
//                    notifyDataSetChanged()
                    //notifyItemChanged(adapterPosition)
                }
                true
            }
            with(binding) {
                selected.setOnClickListener {
                    if (selectedMode) {

                        if (binding.selected.isChecked ) {
                            item.isSelected = true
                            itemView.setBackgroundColor(Color.DKGRAY)
                            //binding.selected.isChecked = true
                            //notifyItemChanged(adapterPosition)
                            selectedPersonas.add(item)
                        } else {
                            item.isSelected = false
                            itemView.setBackgroundColor(Color.argb(255, 0, 255, 255))
                            selectedPersonas.remove(item)
                            //binding.selected.isChecked = false
                            //notifyItemChanged(adapterPosition)

                        }
                        actions.itemHasClicked(item)
                    }
                }

                tvNombre.text = item.orderDate.toString()
                tvId.text = item.id.toString()
                if (selectedMode)
                    selected.visibility = View.VISIBLE
                else{
                    item.isSelected = false
                    selected.visibility = View.GONE
                }

                if (selectedPersonas.contains(item)) {
                    itemView.setBackgroundColor(Color.DKGRAY)
                    binding.selected.isChecked = true
                    //selected.visibility = View.VISIBLE
                } else {
                    itemView.setBackgroundColor(Color.argb(255, 0, 62, 48))
                    binding.selected.isChecked = false
                }
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
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    val position = viewHolder.bindingAdapterPosition
                    selectedPersonas.remove(currentList[position])
                    actions.onDelete(currentList[position])
                    if (selectedMode)
                        actions.itemHasClicked(currentList[position])
                }
            }
        }
    }
}


