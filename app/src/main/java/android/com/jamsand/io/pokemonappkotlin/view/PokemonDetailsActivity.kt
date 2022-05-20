package android.com.jamsand.io.pokemonappkotlin.view

import android.com.jamsand.io.pokemonappkotlin.R
import android.com.jamsand.io.pokemonappkotlin.databinding.ActivityMainBinding
import android.com.jamsand.io.pokemonappkotlin.databinding.ActivityPokemonDetailsBinding
import android.com.jamsand.io.pokemonappkotlin.model.Details
import android.com.jamsand.io.pokemonappkotlin.network.PokemonDetailsApiService
import android.com.jamsand.io.pokemonappkotlin.network.PokemonListApiService
import android.com.jamsand.io.pokemonappkotlin.repository.PokemonDetailsRepository
import android.com.jamsand.io.pokemonappkotlin.repository.PokemonRepository
import android.com.jamsand.io.pokemonappkotlin.utilities.EXTRA_POKEMON
import android.com.jamsand.io.pokemonappkotlin.utilities.EXTRA_POKEMON_ID
import android.com.jamsand.io.pokemonappkotlin.viewmodel.PokemonDetailsViewModel
import android.com.jamsand.io.pokemonappkotlin.viewmodel.PokemonDetailsViewModelFactory
import android.com.jamsand.io.pokemonappkotlin.viewmodel.PokemonListViewModel
import android.com.jamsand.io.pokemonappkotlin.viewmodel.PokemonListViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_pokemon_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonDetailsActivity : AppCompatActivity() {

    private val TAG = "PokemonDetailsActivity"
    private lateinit var pokemonName:String
     var pokemonID:Int = 0

    private lateinit var binding: ActivityPokemonDetailsBinding
    private lateinit var pokemonDetailsViewModel: PokemonDetailsViewModel
    private val retrofitService = PokemonDetailsApiService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val actionbar = supportActionBar
        actionbar!!.title = "Back"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        val bundle:Bundle = intent.extras!!
        pokemonID = bundle.getInt(EXTRA_POKEMON_ID)
        pokemonName = bundle.get(EXTRA_POKEMON) as String

        init()

        setWidgets()
       // observePokemonDetailsData(1)

        getPokemonDetails(pokemonID)
    }

    private fun setWidgets(){
        pokemonDetailsName.text = pokemonName
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun init(){
        pokemonDetailsViewModel = ViewModelProvider(this,
            PokemonDetailsViewModelFactory(PokemonDetailsRepository(retrofitService))
        ).get(PokemonDetailsViewModel::class.java)
    }
    private fun observePokemonDetailsData(id:Int){
        pokemonDetailsViewModel.getPokemonDetails(id)
        pokemonDetailsViewModel.pokemonDetails.observe(this, Observer {

            Log.d(TAG,"New onCreate: ${it.name}")
        })
        pokemonDetailsViewModel.errorMessage.observe(this, Observer {
//
        })
      //  pokemonDetailsViewModel.getPokemonDetails(id)
//        pokemonDetailsViewModel.pokemonList.observe(this, Observer {
//            Log.d(TAG,"onCreate: $it")
//            adapter.setPokemonList(it)
//
//        })
//        listViewModel.errorMessage.observe(this, Observer {
//
//        })
//        listViewModel.getAllPokemons()
    }

    private fun getPokemonDetails(id: Int){

        val apiInterface = PokemonDetailsApiService.getInstance().getPokemonDetails(id)
        apiInterface.enqueue( object : Callback<Details>{

            override fun onResponse(call: Call<Details>, response: Response<Details>) {
                if (response.isSuccessful){
                    Log.d(TAG, response.body().toString())
                    Glide.with(binding.imageView)
                        .load(response.body()?.sprites?.other?.home?.front_default)
                        .into(binding.imageView)

                    binding.heightTextView.text ="Height: ${response.body()?.height.toString()} cm"
                    binding.weightTextView.text = "Weight: ${response.body()?.weight} kg"
                 //   binding.typeTextView.text = "Types: "+response.body()?.types
                    val typesBuilder = StringBuilder()
                    for (types in response.body()?.types!!) {
                        typesBuilder.append(types.type.name)
                    }
                    binding.typeTextView.text = "Types: $typesBuilder "


                }
                else {
                    Log.d(TAG, response.body().toString())
                }
            }
            override fun onFailure(call: Call<Details>, t: Throwable) {
                Log.d(TAG,"${t.message}")
            }
        })
    }
}