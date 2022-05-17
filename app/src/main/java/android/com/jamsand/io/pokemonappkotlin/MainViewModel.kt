package android.com.jamsand.io.pokemonappkotlin

import android.com.jamsand.io.pokemonappkotlin.activity.PokemonDetails
import android.com.jamsand.io.pokemonappkotlin.adapter.PokemonAdapter
import android.com.jamsand.io.pokemonappkotlin.model.Pokemon
import android.com.jamsand.io.pokemonappkotlin.utilities.EXTRA_POKEMON
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository):
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