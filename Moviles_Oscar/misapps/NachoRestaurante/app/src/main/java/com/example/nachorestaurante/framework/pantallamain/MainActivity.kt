package com.example.nachorestaurante.framework.pantallamain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.nachorestaurante.R
import com.example.nachorestaurante.databinding.ActivityMainBinding
import com.example.nachorestaurante.domain.modelo.Customer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var primeraVez: Boolean = false
    private lateinit var customAdapter: CustomerAdapter
    private val viewModel: MainViewModel by viewModels()
    private var isInActionMode: Boolean = false
    private val callback by lazy {
        configContextBar()
    }
    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        primeraVez = true
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customAdapter = CustomerAdapter(this,
            object : CustomerAdapter.PersonaActions {
                override fun onDelete(customer: Customer) =
                    viewModel.handleEvent(MainEvent.DeletePersona(customer))

                override fun onStartSelectMode(customer: Customer) {
                    viewModel.handleEvent(MainEvent.StartSelectMode)
                    viewModel.handleEvent(MainEvent.SeleccionaPersona(customer))
                }

                override fun itemHasClicked(customer: Customer) {
                    viewModel.handleEvent(MainEvent.SeleccionaPersona(customer))
                }
            })

        //configurar el recyclerview
        binding.rvPersonas.adapter = customAdapter
        val touchHelper = ItemTouchHelper(customAdapter.swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvPersonas)

        //observar los cambios en el estado del viewmodel
        viewModel.uiState.observe(this) { state ->
            //si la lista de personas cambia, se actualiza el adapter y se cambia en pantalla
            state.personas.let {
                if (it.isNotEmpty()) {
                    customAdapter.submitList(it)
                }
            }

            //si la lista de personas seleccionadas cambia, se actualiza el adapter y se cambia en pantalla junto al titulo del actionmode
            state.personasSeleccionadas.let {
                if (it.isNotEmpty()) {
                    customAdapter.setSelectedItems(it)
                    actionMode?.title = "${it.size} selected"
                } else {
                    customAdapter.resetSelectMode()
                    primeraVez = true
                    actionMode?.finish()
                }
            }

            //si el modo seleccion cambia, se llama al adapter para que cambie el modo seleccion
            state.selectMode.let { seleccionado ->
                if (seleccionado) {
                    if (primeraVez) {
                        customAdapter.startSelectMode()
                        //si es la primera vez que se entra en modo seleccion, se crea el actionmode, que es el menu contextual
                        if (isInActionMode) {
                            startSupportActionMode(callback)?.let {
                                actionMode = it
                            }
                        }

                        primeraVez = false
                    } else {
                        customAdapter.startSelectMode()
                    }
                } else {//si se sale del modo seleccion, se llama al adapter para que cambie el modo seleccion
                    customAdapter.resetSelectMode()
                    primeraVez = true
                    actionMode?.finish()//se cierra el actionmode

                }
            }

            state.error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
        configAppBar()
    }

    private fun configContextBar() = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            isInActionMode = true
            menuInflater.inflate(R.menu.context_bar, menu)
            binding.topAppBar.visibility = android.view.View.GONE
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.more -> {
                    viewModel.handleEvent(MainEvent.DeletePersonasSeleccionadas())
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            isInActionMode = false
            viewModel.handleEvent(MainEvent.ResetSelectMode)
            binding.topAppBar.visibility = android.view.View.VISIBLE
            customAdapter.resetSelectMode()
        }
    }

    private fun configAppBar() {

        val actionSearch = binding.topAppBar.menu.findItem(R.id.search).actionView as SearchView

        actionSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //TODO filtro final sin el contains
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filtro ->
                    viewModel.handleEvent(MainEvent.GetPersonaFiltradas(filtro))
                }
                return true
            }
        })
    }

}