package android.com.jamsand.io.pokemonappkotlin.repository

import android.com.jamsand.io.pokemonappkotlin.network.ApiService

class PokemonRepository constructor(private val retrofitService: ApiService){

    fun getAllPokemons () = retrofitService.getPokemons()

}