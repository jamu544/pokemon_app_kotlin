package android.com.jamsand.io.pokemonappkotlin.viewmodel

import android.com.jamsand.io.pokemonappkotlin.model.Pokemon
import android.com.jamsand.io.pokemonappkotlin.repository.RepositoryObject
import android.com.jamsand.io.pokemonappkotlin.utilities.Utility.isInternetAvailable
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class PokemonViewModel(context: Context): ViewModel() {
    private var listData = MutableLiveData<List<Pokemon.PokemonArray>>()

     init {
         val pokemonRepository: RepositoryObject by lazy {
             RepositoryObject
         }
         if(context.isInternetAvailable()) {
             listData = pokemonRepository.getMutableLiveData(context)
         }
     }
    fun getData() : MutableLiveData<List<Pokemon.PokemonArray>>{
        return listData
    }

}


