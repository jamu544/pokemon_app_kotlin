package android.com.jamsand.io.pokemonappkotlin.repository

import android.com.jamsand.io.pokemonappkotlin.network.PokemonDetailsApiService

class PokemonDetailsRepository constructor(private val
                             retrofitServicePokemonDetails: PokemonDetailsApiService){

    fun getPokemonDetails (id:Int) = retrofitServicePokemonDetails.getPokemonDetails(0)

}