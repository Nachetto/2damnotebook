package com.example.nachorestaurante.framework.pantalladetallada

import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.nachorestaurante.R
import com.example.nachorestaurante.databinding.ActivityDetailedBinding
import dagger.hilt.android.AndroidEntryPoint


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.nachorestaurante.domain.modelo.Order

@AndroidEntryPoint
class DetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedBinding
    private lateinit var orderAdapter: OrderAdapter
    private val viewModel: DetailedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orderAdapter = OrderAdapter(this,
            object : OrderAdapter.OrderActions {
                override fun onDelete(order: Order) =
                    viewModel.handleEvent(DetailedEvent.DeleteOrder(order))
            })

        binding.rvOrders.adapter = orderAdapter
        val touchHelper = ItemTouchHelper(orderAdapter.swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvOrders)

        val id = intent.getIntExtra("CustomerDetailed", 0)
        viewModel.handleEvent(DetailedEvent.GetCustomer(id))
        viewModel.handleEvent(DetailedEvent.GetOrders(id))

        viewModel.uiState.observe(this) { state ->
            state.persona?.let {
                binding.tvName.text = it.name + " " + it.surname
                binding.tvEmail.text = it.email
                binding.tvPhone.text = it.phone.toString()
            }

            state.orders.let {
                orderAdapter.submitList(it)
            }

            state.error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        binding.button.setOnClickListener {
            viewModel.handleEvent(DetailedEvent.AddOrder(binding.tableId.text.toString().toInt()))
        }
    }

}