package com.example.ejerciciodatajsonlocal.ui.pantallas.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ejerciciodatajsonlocal.databinding.ItemPokemonBinding
import com.example.ejerciciodatajsonlocal.domain.model.Pokemon

class PokemonAdapter(
    private var pokemon: List<Pokemon>,
    private val pokemonButtonClickedListener: (Pokemon) -> Unit
) : RecyclerView.Adapter<PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return PokemonViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemon[position]
        holder.bind(pokemon, pokemonButtonClickedListener)
        holder.itemView.setOnClickListener {
            pokemonButtonClickedListener(pokemon)
        }
    }

    override fun getItemCount() = pokemon.size
}

class PokemonViewHolder(private val binding: ItemPokemonBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        pokemon: Pokemon,
        onClickButton: (Pokemon) -> Unit
    ) {
        binding.txtNombrePokemon.text = pokemon.nombre
        binding.buttonPokemon.setOnClickListener {
            onClickButton(pokemon)
        }
    }
}
