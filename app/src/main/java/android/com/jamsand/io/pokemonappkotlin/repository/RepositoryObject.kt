package android.com.jamsand.io.pokemonappkotlin.repository

import android.com.jamsand.io.pokemonappkotlin.model.Pokemon
import android.com.jamsand.io.pokemonappkotlin.network.ApiClient
import android.com.jamsand.io.pokemonappkotlin.utilities.Utility.hideProgressBar
import android.com.jamsand.io.pokemonappkotlin.utilities.Utility.showProgressBar
import android.content.Context
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RepositoryObject {
    fun getMutableLiveData(context: Context) : MutableLiveData<List<Pokemon.PokemonArray>>
    {
        val mutableLiveData = MutableLiveData<List<Pokemon.PokemonArray>>()
        context.showProgressBar()

        ApiClient.apiService.getPokemon().enqueue( object : Callback<Pokemon> {

            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                hideProgressBar()
                val usersResponse = response.body()
                usersResponse?.let { mutableLiveData.value = it as ArrayList<Pokemon.PokemonArray> }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                hideProgressBar()

            }


        })

        return mutableLiveData
    }
}