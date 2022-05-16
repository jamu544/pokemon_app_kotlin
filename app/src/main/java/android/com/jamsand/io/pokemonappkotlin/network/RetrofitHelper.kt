package android.com.jamsand.io.pokemonappkotlin.network

import android.com.jamsand.io.pokemonappkotlin.utilities.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper  {
    fun getInstance():Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}