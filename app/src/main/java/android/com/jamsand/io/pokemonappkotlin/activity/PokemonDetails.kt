package android.com.jamsand.io.pokemonappkotlin.activity

import android.com.jamsand.io.pokemonappkotlin.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PokemonDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)
    }
}