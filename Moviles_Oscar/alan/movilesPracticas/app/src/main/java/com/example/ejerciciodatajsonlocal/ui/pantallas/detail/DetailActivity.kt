package com.example.ejerciciodatajsonlocal.ui.pantallas.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.ejerciciodatajsonlocal.data.Repository
import com.example.ejerciciodatajsonlocal.databinding.ActivityDetailBinding
import com.example.ejerciciodatajsonlocal.domain.model.Pokemon
import com.example.ejerciciodatajsonlocal.domain.usecases.AddPokemonUseCase
import com.example.ejerciciodatajsonlocal.domain.usecases.DeletePokemonUseCase
import com.example.ejerciciodatajsonlocal.domain.usecases.GetNextIdPokemonUseCase
import com.example.ejerciciodatajsonlocal.domain.usecases.UpdatePokemonUseCase

class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(
            AddPokemonUseCase(Repository()),
            GetNextIdPokemonUseCase(Repository()),
            DeletePokemonUseCase(Repository()),
            UpdatePokemonUseCase(Repository()),
        )
    }

    private lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        showPokemonFromIntent()
        eventos()
        observarViewModel()
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(this) { state ->
            state.message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.errorShownAlready()
            }
        }
    }

    private fun eventos() {
        buttonAddSetOnClickListener()
        buttonDeleteSetOnClickListener()
        buttonUpdateSetOnClickListener()
        binding.buttonShowAll.setOnClickListener {
            finish()
        }
    }

    private fun buttonUpdateSetOnClickListener() {
        with(binding) {
            buttonUpdate.setOnClickListener {
                viewModel.updatePokemon(
                    pokemonId = intIdPokemon.text.toString().toInt(),
                    name = txtPokemonName.text.toString(),
                    experienciaBase = intExperienciaBasePokemon.text.toString().toInt(),
                    height = intAltura.text.toString().toInt(),
                    weight = intPesoPokemon.text.toString().toInt(),
                )
            }
        }
    }

    private fun buttonDeleteSetOnClickListener() {
        with(binding) {
            buttonDelete.setOnClickListener {
                viewModel.deletePokemon(
                    pokemonId = intIdPokemon.text.toString().toInt()
                )
                finish()
            }
        }
    }

    private fun buttonAddSetOnClickListener() {
        with(binding) {
            buttonAdd.setOnClickListener {
                viewModel.addPokemon(
                    name = txtPokemonName.text.toString(),
                    experienciaBase = intExperienciaBasePokemon.text.toString().toInt(),
                    altura = intAltura.text.toString().toInt(),
                    peso = intPesoPokemon.text.toString().toInt(),
                    imagen = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                    tipoPokemon = txtPokemonType.text.toString()
                )
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun showPokemonFromIntent() {
        val pokemon = intent.getParcelableExtra<Pokemon>("DetailActivity:pokemon")

        if (pokemon != null) {
            with(binding) {
                intIdPokemon.text = pokemon.id.toString()
                txtPokemonName.setText(pokemon.nombre)
                intAltura.setText(pokemon.altura.toString())
                intPesoPokemon.setText(pokemon.peso.toString())
                intExperienciaBasePokemon.setText(pokemon.experienciaBase.toString())
                imageView.load(pokemon.imagen)
                txtPokemonType.setText(pokemon.tipoPokemon)
            }
        } else {
            Toast.makeText(this, "Error al cargar el pokemon", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}

