package android.com.jamsand.io.pokemonappkotlin.viewmodel

import android.com.jamsand.io.pokemonappkotlin.model.Pokemon
import android.com.jamsand.io.pokemonappkotlin.repository.PokemonRepository
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonViewModel constructor(private val repository: PokemonRepository):
ViewModel(){

    val pokemonList = MutableLiveData<List<Pokemon.PokemonArray>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllPokemons (){
        val response = repository.getAllPokemons()
        response.enqueue (object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>?, response: Response<Pokemon>?) {
                if(response?.isSuccessful!= null) {

                    pokemonList.postValue(response.body()?.results)
                    // recyclerAdapter.setMovieListItems(response.body()!!)
                    Log.d("VIEW MODEL", response.body()!!.results.toString())
                }
            }

            override fun onFailure(call: Call<Pokemon>?, t: Throwable?) {
                if (t != null) {
                    errorMessage.postValue(t.message)
                }

            }
        })
    }
}