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
class DetailedActivity : AppCompatActivity(){

    private lateinit var binding: ActivityDetailedBinding
    private var primeraVez: Boolean = false
    private lateinit var orderAdapter: OrderAdapter
    private val viewModel: DetailedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        primeraVez = true
        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //manejar los gestos de deslizamiento y longclick enviandolos al viewmodel
        orderAdapter = OrderAdapter(this,
            object : OrderAdapter.OrderActions {
                override fun onDelete(order: Order) =
                    viewModel.handleEvent(DetailedEvent.DeleteOrder(order))
            })

        //configurar el recyclerview
        binding.rvOrders.adapter = orderAdapter
        val touchHelper = ItemTouchHelper(orderAdapter.swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvOrders)

        //meter los orders a la tabla y cargar el Customer asociado
        val id = intent.getIntExtra("CustomerDetailed", 0)
        viewModel.handleEvent(DetailedEvent.GetCustomer(id))
        viewModel.handleEvent(DetailedEvent.GetOrders(id))

        //observar los cambios en el estado del viewmodel
        viewModel.uiState.observe(this) { state ->
            //si cambia la persona se lo mete a datos de pantalla
            state.persona?.let {
                binding.tvName.text = it.name+" "+it.surname
                binding.tvEmail.text = it.email
                binding.tvPhone.text = it.phone.toString()
            }

            //si la lista de orders cambia, se actualiza el adapter y se cambia en pantalla
            state.orders.let {
                if (it.isNotEmpty()) {
                    orderAdapter.submitList(it)
                }
            }

            //si hay un error, se muestra en pantalla y se resetea el estado del viewmodel
            state.error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

}