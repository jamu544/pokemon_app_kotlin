package android.com.jamsand.io.pokemonappkotlin

import android.com.jamsand.io.pokemonappkotlin.network.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService){

    fun getAllPokemons () = retrofitService.getPokemons()
}