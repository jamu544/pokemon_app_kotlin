package android.com.jamsand.io.pokemonappkotlin.view

import android.com.jamsand.io.pokemonappkotlin.R
import android.com.jamsand.io.pokemonappkotlin.utilities.EXTRA_POKEMON
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_pokemon_details.*

class PokemonDetails : AppCompatActivity() {

    private lateinit var pokemonName:String
    private lateinit var pokemonID:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        val actionbar = supportActionBar
        actionbar!!.title = "Back"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        val pokemonIntentExtra = intent.extras
        if (pokemonIntentExtra != null){
             pokemonName = pokemonIntentExtra.getString(EXTRA_POKEMON)!!
         //    pokemonID = pokemonIntentExtra?.getString("input")!!
        }
        setWidgets()

    }

    private fun setWidgets(){
        pokemonDetailsName.text = pokemonName
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}