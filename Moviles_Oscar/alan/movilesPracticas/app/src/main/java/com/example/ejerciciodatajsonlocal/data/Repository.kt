package com.example.ejerciciodatajsonlocal.data

import com.example.ejerciciodatajsonlocal.domain.model.Pokemon
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.InputStream
import java.lang.reflect.Type
import java.util.stream.Collectors

class Repository(file: InputStream? = null) {

    companion object {
        private val pokemons = mutableListOf<Pokemon>()
    }

    init {
        if (pokemons.size == 0) {
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

            val listOfCardsType: Type = Types.newParameterizedType(
                MutableList::class.java, Pokemon::class.java
            )
            val pokemonList = file?.bufferedReader()?.readText()?.let { contenidoFichero ->
                moshi.adapter<List<Pokemon>>(listOfCardsType).fromJson(contenidoFichero)
            }


            pokemons.addAll(pokemonList!!)

        }

    }

    fun getListAllPokemons(): List<Pokemon> {
        return pokemons
    }

    fun getNextIdPokemon(): Int {
        return pokemons[pokemons.lastIndex].id + 1
    }

    fun addPokemon(pokemon: Pokemon) = pokemons.add(pokemon)

    fun deletePokemon(pokemonId: Int): Boolean {
        val pokemon: Pokemon = pokemons.stream().filter { pokemon -> pokemon.id == pokemonId }
            .collect(Collectors.toList())[0]

        return pokemons.remove(pokemon)
    }

    fun updatePokemon(
        pokemonId: Int,
        pokemonName: String,
        pokemonExperienciaBase: Int,
        pokemonWeight: Int,
        pokemonHeight: Int,
    ): Boolean {


        pokemons.replaceAll { pokemon ->
            if (pokemon.id == pokemonId) {
                Pokemon(
                    id = pokemonId,
                    nombre = pokemonName,
                    experienciaBase = pokemonExperienciaBase,
                    altura = pokemonHeight,
                    peso = pokemonWeight,
                    imagen = pokemon.imagen,
                    tipoPokemon = pokemon.tipoPokemon
                )
            } else {
                pokemon
            }
        }

        val pokemon: Pokemon = pokemons.stream().filter { pokemon -> pokemon.id == pokemonId }
            .collect(Collectors.toList())[0]


        return pokemon.nombre == pokemonName && pokemon.experienciaBase == pokemonExperienciaBase && pokemon.altura == pokemonHeight && pokemon.peso == pokemonWeight
    }


}