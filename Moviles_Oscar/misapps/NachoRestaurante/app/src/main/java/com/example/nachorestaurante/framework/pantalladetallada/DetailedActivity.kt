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
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.nachorestaurante.domain.modelo.Order

@AndroidEntryPoint
class DetailedActivity : AppCompatActivity(){

    private lateinit var binding: ActivityDetailedBinding
    private var primeraVez: Boolean = false
    private lateinit var orderAdapter: OrderAdapter
    private val viewModel: DetailedViewModel by viewModels()
    private val callback by lazy {
        configContextBar()
    }

    private var actionMode: ActionMode? = null //esto es el menu contextual que se muestra cuando se pulsa un elemento de la lista durante un tiempo largo

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

                override fun onStartSelectMode(order: Order) {
                    viewModel.handleEvent(DetailedEvent.StartSelectMode)
                    viewModel.handleEvent(DetailedEvent.SeleccionaOrder(order))
                }

                override fun itemHasClicked(order: Order) {

                    viewModel.handleEvent(DetailedEvent.SeleccionaOrder(order))
                }
            })

        //configurar el recyclerview
        binding.rvOrders.adapter = orderAdapter
        val touchHelper = ItemTouchHelper(orderAdapter.swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvOrders)

        //observar los cambios en el estado del viewmodel
        viewModel.uiState.observe(this) { state ->
            //si la lista de orders cambia, se actualiza el adapter y se cambia en pantalla
            state.orders.let {
                if (it.isNotEmpty()) {
                    orderAdapter.submitList(it)
                }
            }

            //si la lista de orders seleccionadas cambia, se actualiza el adapter y se cambia en pantalla junto al titulo del actionmode
            state.ordersSeleccionados.let {
                if (it.isNotEmpty()) {
                    orderAdapter.setSelectedItems(it)
                    actionMode?.title = "${it.size} selected"
                } else {
                    orderAdapter.resetSelectMode()
                    primeraVez = true
                    actionMode?.finish()
                }
            }

            //si el modo seleccion cambia, se llama al adapter para que cambie el modo seleccion
            state.selectMode.let { seleccionado ->
                if (seleccionado) {
                    if (primeraVez) {
                        orderAdapter.startSelectMode()
                        //si es la primera vez que se entra en modo seleccion, se crea el actionmode, que es el menu contextual
                        startSupportActionMode(callback)?.let {
                            actionMode = it;
                        }
                        primeraVez = false
                    }
                    else{
                        orderAdapter.startSelectMode()
                    }
                } else {//si se sale del modo seleccion, se llama al adapter para que cambie el modo seleccion
                    orderAdapter.resetSelectMode()
                    primeraVez = true
                    actionMode?.finish()//se cierra el actionmode
                }
            }

            //si hay un error, se muestra en pantalla y se resetea el estado del viewmodel
            state.error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
        val id = intent.getIntExtra("CustomerDetailed", 0)
        viewModel.handleEvent(DetailedEvent.GetOrders(id))
    }

    private fun configContextBar() = object : ActionMode.Callback {
        // esto es para el menu contextual que se muestra cuando se pulsa un elemento de la lista durante un tiempo
        // largo. Se muestra en la parte superior de la pantalla y tiene tres opciones: favoritos, buscar y mas.
        // La opcion favoritos no hace nada, la opcion buscar no hace nada y la opcion mas borra los elementos
        // seleccionados de la lista y sale del modo seleccion
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.context_bar, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.favorite -> {
                    // Handle share icon press
                    true
                }

                R.id.search -> {
                    // Handle delete icon press
                    true
                }

                R.id.more -> {
                    viewModel.handleEvent(DetailedEvent.DeleteOrdersSeleccionados())
                    true
                }

                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            viewModel.handleEvent(DetailedEvent.ResetSelectMode)
        }
    }

    private fun configAppBar() {
        //configurar la appbar para que tenga un menu de busqueda y un menu de opciones en la esquina
        // superior derecha de la pantalla y un menu de navegacion en la esquina superior izquierda de
        // la pantalla que no hace nada al pulsarlo (no hay navegacion) y que se pueda cerrar el menu
        // de navegacion pulsando en cualquier parte de la pantalla que no sea el menu de navegacion
        // o el menu de opciones o el menu de busqueda o el actionmode

        val actionSearch = binding.topAppBar.menu.findItem(R.id.search).actionView as SearchView

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite -> {
                    // Handle favorite icon press
                    true
                }

                R.id.search -> {
                    // Handle search icon press
                    true
                }

                R.id.more -> {
                    // Handle more item (inside overflow menu) press
                    true
                }

                else -> false
            }
        }
    }
}