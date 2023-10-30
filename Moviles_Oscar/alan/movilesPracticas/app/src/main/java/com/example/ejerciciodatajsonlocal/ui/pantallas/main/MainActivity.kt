package com.example.ejerciciodatajsonlocal.ui.pantallas.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.ejerciciodatajsonlocal.data.Repository
import com.example.ejerciciodatajsonlocal.databinding.ActivityMainBinding
import com.example.ejerciciodatajsonlocal.domain.model.Pokemon
import com.example.ejerciciodatajsonlocal.domain.usecases.GetAllPokemonUseCase
import com.example.ejerciciodatajsonlocal.ui.pantallas.detail.DetailActivity
import com.example.ejerciciodatajsonlocal.ui.pantallas.detail.adapter.PokemonAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PokemonAdapter

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            GetAllPokemonUseCase(
                Repository(assets.open("data.json"))),
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        observarViewModel()
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(this@MainActivity) { state ->
            state.pokemons.let { listPokemon ->
                adapter = PokemonAdapter(listPokemon) { navigateTo(it) }
                binding.rvPokemon.adapter = adapter
            }
        }
    }

    private fun navigateTo(pokemon: Pokemon) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("DetailActivity:pokemon", pokemon)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        viewModel.cargarPokemons()
    }
}