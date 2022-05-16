package android.com.jamsand.io.pokemonappkotlin.activity

import android.com.jamsand.io.pokemonappkotlin.R
import android.com.jamsand.io.pokemonappkotlin.adapter.PokemonAdapter
import android.com.jamsand.io.pokemonappkotlin.model.Pokemon
import android.com.jamsand.io.pokemonappkotlin.network.ApiInterface
import android.com.jamsand.io.pokemonappkotlin.utilities.EXTRA_POKEMON
import android.com.jamsand.io.pokemonappkotlin.utilities.EXTRA_POKEMON_ID
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var  recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        manager = LinearLayoutManager(this)
        getAllData()
    }


    private fun getAllData() {
        val pokemonApi = ApiInterface.create().getPokemons()
        pokemonApi.enqueue( object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>?, response: Response<Pokemon>?) {

                if(response?.isSuccessful!= null) {
                    recyclerView = findViewById<RecyclerView>(R.id.recyclerview).apply {
                        myAdapter = PokemonAdapter(context,response.body()!!.results){pokemon ->
                            val productIntent = Intent(context, PokemonDetails::class.java)
//                            productIntent.putExtra(EXTRA_POKEMON, category.title)
//                            productIntent.putExtra(EXTRA_POKEMON_ID, category.title)
                            startActivity(productIntent)
                        }
                        layoutManager = manager
                        adapter = myAdapter
                    }

                    // recyclerAdapter.setMovieListItems(response.body()!!)
                    Log.d("LIST", response.body()!!.results.toString())
                }
            }

            override fun onFailure(call: Call<Pokemon>?, t: Throwable?) {

            }
        })

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
    }
}
