package android.com.jamsand.io.pokemonappkotlin.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PokemonViewModelFactory(private val context: Context):
ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PokemonViewModel(context) as T
    }

}