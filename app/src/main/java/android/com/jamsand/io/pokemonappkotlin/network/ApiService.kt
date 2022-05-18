package android.com.jamsand.io.pokemonappkotlin.network

import android.com.jamsand.io.pokemonappkotlin.model.Pokemon
import android.com.jamsand.io.pokemonappkotlin.utilities.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("https://pokeapi.co/api/v2/pokemon/")
//    fun getPokemons(): Call<MutableList<Pokemon>>
     fun getPokemons(): Call<Pokemon>
    companion object {
        var retrofitService: ApiService? = null
        fun getInstance() : ApiService {
            if(retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
                retrofitService = retrofit.create(ApiService::class.java)
            }
            return retrofitService!!

        }
    }


}