package android.com.jamsand.io.pokemonappkotlin.view

import android.com.jamsand.io.pokemonappkotlin.repository.PokemonRepository
import android.com.jamsand.io.pokemonappkotlin.viewmodel.PokemonViewModel
import android.com.jamsand.io.pokemonappkotlin.viewmodel.PokemonViewModelFactory
import android.com.jamsand.io.pokemonappkotlin.adapter.PokemonAdapter
import android.com.jamsand.io.pokemonappkotlin.databinding.ActivityMainBinding
import android.com.jamsand.io.pokemonappkotlin.network.ApiService
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
    private lateinit var viewModel: PokemonViewModel
    private val retrofitService = ApiService.getInstance()
   // val adapter = PokemonAdapter()
    private lateinit var adapter:PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        getAllData()

    }

    private fun init(){
        adapter = PokemonAdapter(OnClickListener { pokemon ->

            loadPokemonDetailsActivity(pokemon.name,pokemon.pokemonID)
            Toast.makeText(applicationContext, "${pokemon.name}",
                Toast.LENGTH_SHORT).show() })

        viewModel = ViewModelProvider(this,
            PokemonViewModelFactory(PokemonRepository(retrofitService))
        ).get(PokemonViewModel::class.java)


        binding.recyclerview.adapter = adapter
    }

    private fun loadPokemonDetailsActivity(name:String,pokemonID: Int ){
        val pokemonIntent = Intent(this, PokemonDetails::class.java)
        pokemonIntent.putExtra(EXTRA_POKEMON_ID, pokemonID)
        pokemonIntent.putExtra(EXTRA_POKEMON, name)

        startActivity(pokemonIntent)
    }
    private fun getAllData(){

        viewModel.pokemonList.observe(this, Observer {
            Log.d(TAG,"onCreate: $it")
            adapter.setPokemonList(it)

        })
        viewModel.errorMessage.observe(this, Observer {

        })
        viewModel.getAllPokemons()
    }


//    private fun getAllData() {
//        val pokemonApi = ApiService.create().getPokemons()
//        pokemonApi.enqueue( object : Callback<Pokemon> {
//            override fun onResponse(call: Call<Pokemon>?, response: Response<Pokemon>?) {
//
//                if(response?.isSuccessful!= null) {
//                    recyclerView = findViewById<RecyclerView>(R.id.recyclerview).apply {
//                        myAdapter = PokemonAdapter(context,response.body()!!.results){pokemon ->
//                            val productIntent = Intent(context, PokemonDetails::class.java)
//                            productIntent.putExtra(EXTRA_POKEMON, pokemon.name)
//                         //   productIntent.putExtra(EXTRA_POKEMON_ID,pokemon.)
//                            Toast.makeText(context, pokemon.name,Toast.LENGTH_SHORT).show()
//                            startActivity(productIntent)
//                        }
//                        layoutManager = manager
//                        adapter = myAdapter
//                    }
//
//                    // recyclerAdapter.setMovieListItems(response.body()!!)
//                    Log.d("LIST", response.body()!!.results.toString())
//                }
//            }
//
//            override fun onFailure(call: Call<Pokemon>?, t: Throwable?) {
//
//            }
//        })

        // launching a new coroutine
//        GlobalScope.launch {
//            val result = pokemonApi.getPokemons()
//            if (result != null) {
//                // Checking the results
//
//                var str_response = result.body()!!.toString()
//                //creating json object
//                val json_contact:JSONObject = JSONObject(str_response.ge)
//                Log.d("ayush: ", json_contact.toString())
//
////                val json = result.body().toString()
////                val obj = JSONObject(json)
////                val getObject = obj.getJSONObject("results")
////
////                Log.d("ayush: ",getObject.toString())
//
//
//            }
//        }
//    }
}
