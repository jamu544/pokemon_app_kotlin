package android.com.jamsand.io.pokemonappkotlin.view

import android.com.jamsand.io.pokemonappkotlin.repository.PokemonRepository
import android.com.jamsand.io.pokemonappkotlin.viewmodel.PokemonListViewModel
import android.com.jamsand.io.pokemonappkotlin.viewmodel.PokemonListViewModelFactory
import android.com.jamsand.io.pokemonappkotlin.adapter.PokemonAdapter
import android.com.jamsand.io.pokemonappkotlin.databinding.ActivityMainBinding
import android.com.jamsand.io.pokemonappkotlin.network.PokemonListApiService
import android.com.jamsand.io.pokemonappkotlin.utilities.EXTRA_POKEMON
import android.com.jamsand.io.pokemonappkotlin.utilities.EXTRA_POKEMON_ID
import android.com.jamsand.io.pokemonappkotlin.utilities.Utility.hideProgressBar
import android.com.jamsand.io.pokemonappkotlin.utilities.Utility.isInternetAvailable
import android.com.jamsand.io.pokemonappkotlin.utilities.Utility.showProgressBar
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private lateinit var listViewModel: PokemonListViewModel
    private val retrofitService = PokemonListApiService.getInstance()
    private lateinit var context: Context

    private lateinit var adapter:PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(context.isInternetAvailable()) {
            init()
            observeResponseDataList()
        }

    }

    private fun init(){
        // Create a TypeFace using the font file
        val typeface = Typeface.createFromAsset(assets, "pokemon_solid.ttf")

        binding.pokemonTitleTextView.typeface = typeface
        adapter = PokemonAdapter(OnClickListener { pokemon ->

            loadPokemonDetailsActivity(pokemon.name,pokemon.pokemonID)
            Toast.makeText(applicationContext, "${pokemon.name}",
                Toast.LENGTH_SHORT).show() })

        listViewModel = ViewModelProvider(this,
            PokemonListViewModelFactory(PokemonRepository(retrofitService))
        ).get(PokemonListViewModel::class.java)

        var spanCount = 2
        val orientation = resources.configuration.orientation
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            spanCount = 4
        }
        val screenSize = resources.configuration.screenWidthDp
        if(screenSize > 720){
            spanCount = 3
        }

        val layoutManager = GridLayoutManager(this,spanCount)
        binding.recyclerview.layoutManager = layoutManager


        binding.recyclerview.adapter = adapter
    }

    private fun loadPokemonDetailsActivity(name:String,pokemonID: Int ){
        val pokemonIntent = Intent(this, PokemonDetailsActivity::class.java)
        pokemonIntent.putExtra(EXTRA_POKEMON_ID, pokemonID)
        pokemonIntent.putExtra(EXTRA_POKEMON, name)
        startActivity(pokemonIntent)
    }
    private fun observeResponseDataList(){
        //original code
        context.showProgressBar()
        listViewModel.pokemonList.observe(this, Observer {
            Log.d(TAG,"onCreate: $it")

            adapter.setPokemonList(it)
            hideProgressBar()
        })
        listViewModel.errorMessage.observe(this, Observer {
            hideProgressBar()
        })

        listViewModel.getAllPokemons()

    }

}
