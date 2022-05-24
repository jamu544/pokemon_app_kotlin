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
import android.com.jamsand.io.pokemonappkotlin.utilities.Utility
import android.com.jamsand.io.pokemonappkotlin.utilities.Utility.isInternetAvailable
import android.com.jamsand.io.pokemonappkotlin.utilities.Utility.showProgressBar
import android.com.jamsand.io.pokemonappkotlin.viewmodel.PokemonDetailsViewModel
import android.com.jamsand.io.pokemonappkotlin.viewmodel.PokemonDetailsViewModelFactory
import android.com.jamsand.io.pokemonappkotlin.viewmodel.PokemonListViewModel
import android.com.jamsand.io.pokemonappkotlin.viewmodel.PokemonListViewModelFactory
import android.content.Context
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
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
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this

        val actionbar = supportActionBar
        actionbar!!.title = "Back"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        val bundle:Bundle = intent.extras!!
        pokemonID = bundle.getInt(EXTRA_POKEMON_ID)
        pokemonName = bundle.get(EXTRA_POKEMON) as String

            //check internet connection
        if(context.isInternetAvailable()) {
            init()
            setWidgets()
            // observePokemonDetailsData(1)
            getPokemonDetails(pokemonName)
        }


    }

    private fun setWidgets(){
        val typeface = Typeface.createFromAsset(assets, "pokemon_solid.ttf")
        binding.pokemonDetailsName.typeface = typeface
        binding.pokemonDetailsName.text = pokemonName
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
    private fun observePokemonDetailsData(name:String){
        pokemonDetailsViewModel.getPokemonDetails(name)
        pokemonDetailsViewModel.pokemonDetails.observe(this, Observer {

            Log.d(TAG,"New onCreate: ${it.name}")
        })
        pokemonDetailsViewModel.errorMessage.observe(this, Observer {
//
        })
    }

    private fun getPokemonDetails(name: String){

        val apiInterface = PokemonDetailsApiService.getInstance().getPokemonDetails(name)
        context.showProgressBar()
        apiInterface.enqueue( object : Callback<Details>{

            override fun onResponse(call: Call<Details>, response: Response<Details>) {
                if (response.isSuccessful){
                    Utility.hideProgressBar()
                    Log.d(TAG, response.body().toString())
                    Glide.with(binding.imageView)
                        .load(response.body()?.sprites?.other?.home?.front_default)
                        .into(binding.imageView)

                    binding.heightTextView.text ="Height: ${response.body()?.height.toString()} cm"
                    binding.weightTextView.text = "Weight: ${response.body()?.weight} kg"

                    val listOftypes = ArrayList<String>()
                    for(types in response.body()?.types!!){
                        listOftypes.add(types.type.name)
                    }
                    binding.typeTextView.text = "Types: ${listOftypes.toString()} "


                }
                else {
                    Log.d(TAG, response.body().toString())
                }
            }
            override fun onFailure(call: Call<Details>, t: Throwable) {
                Utility.hideProgressBar()
                Log.d(TAG,"${t.message}")
            }
        })
    }
}