package android.com.jamsand.io.pokemonappkotlin.viewmodel

import android.com.jamsand.io.pokemonappkotlin.model.Details
import android.com.jamsand.io.pokemonappkotlin.network.PokemonDetailsApiService
import android.com.jamsand.io.pokemonappkotlin.repository.PokemonDetailsRepository
import android.com.jamsand.io.pokemonappkotlin.repository.PokemonRepository
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonDetailsViewModel constructor(private val repository: PokemonDetailsRepository):
    ViewModel(){

    val pokemonDetails = MutableLiveData<Details>()
    val errorMessage = MutableLiveData<String>()

    fun getPokemonDetails(id: Int){
        val response = repository.getPokemonDetails(id)
        response.enqueue( object : Callback<Details> {

            override fun onResponse(call: Call<Details>, response: Response<Details>) {
                pokemonDetails.postValue(response.body())
            }
            override fun onFailure(call: Call<Details>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}