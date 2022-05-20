package android.com.jamsand.io.pokemonappkotlin.viewmodel

import android.com.jamsand.io.pokemonappkotlin.repository.PokemonDetailsRepository
import android.com.jamsand.io.pokemonappkotlin.repository.PokemonRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PokemonDetailsViewModelFactory constructor(private val repository: PokemonDetailsRepository):
    ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PokemonDetailsViewModel::class.java)){
            PokemonDetailsViewModel(this.repository) as T }
        else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}