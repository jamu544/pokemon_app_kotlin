package android.com.jamsand.io.pokemonappkotlin.view

import android.com.jamsand.io.pokemonappkotlin.repository.PokemonRepository
import android.com.jamsand.io.pokemonappkotlin.viewmodel.PokemonListViewModel
import android.com.jamsand.io.pokemonappkotlin.viewmodel.PokemonListViewModelFactory
import android.com.jamsand.io.pokemonappkotlin.adapter.PokemonAdapter
import android.com.jamsand.io.pokemonappkotlin.databinding.ActivityMainBinding
import android.com.jamsand.io.pokemonappkotlin.network.PokemonListApiService
import android.com.jamsand.io.pokemonappkotlin.utilities.EXTRA_POKEMON
import android.com.jamsand.io.pokemonappkotlin.utilities.EXTRA_POKEMON_ID
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private lateinit var listViewModel: PokemonListViewModel
    private val retrofitService = PokemonListApiService.getInstance()

    private lateinit var adapter:PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        observeResponseDataList()

    }

    private fun init(){
        adapter = PokemonAdapter(OnClickListener { pokemon ->

            loadPokemonDetailsActivity(pokemon.name,pokemon.pokemonID)
            Toast.makeText(applicationContext, "${pokemon.name}",
                Toast.LENGTH_SHORT).show() })

        listViewModel = ViewModelProvider(this,
            PokemonListViewModelFactory(PokemonRepository(retrofitService))
        ).get(PokemonListViewModel::class.java)


        binding.recyclerview.adapter = adapter
    }

    private fun loadPokemonDetailsActivity(name:String,pokemonID: Int ){
        val pokemonIntent = Intent(this, PokemonDetailsActivity::class.java)
        pokemonIntent.putExtra(EXTRA_POKEMON_ID, pokemonID)
        pokemonIntent.putExtra(EXTRA_POKEMON, name)
        startActivity(pokemonIntent)
    }
    private fun observeResponseDataList(){
        listViewModel.pokemonList.observe(this, Observer {
            Log.d(TAG,"onCreate: $it")
            adapter.setPokemonList(it)
        })
        listViewModel.errorMessage.observe(this, Observer {
        })
        listViewModel.getAllPokemons()
    }
}
