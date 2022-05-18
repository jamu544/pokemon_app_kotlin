package android.com.jamsand.io.pokemonappkotlin.view

import android.com.jamsand.io.pokemonappkotlin.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PokemonDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)
//        val product = intent.getParcelableExtra<Product>(EXTRA_PRODUCT)
//        val resourceId = resources.getIdentifier(product?.image,"drawable",packageName)
//
//        detailImageView.setImageResource(resourceId)
//        detailProductName.text = product?.title
//        detailProductPrice.text = product?.price
    }
}