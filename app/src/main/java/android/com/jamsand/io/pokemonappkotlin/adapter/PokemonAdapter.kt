package android.com.jamsand.io.pokemonappkotlin.adapter

import android.com.jamsand.io.pokemonappkotlin.R
import android.com.jamsand.io.pokemonappkotlin.model.Pokemon
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PokemonAdapter (val context: Context, val pokemons: List<Pokemon.PokemonArray>, val itemClick:(Pokemon.PokemonArray)-> Unit):
    RecyclerView.Adapter<PokemonAdapter.PokemonHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pokemon_list_item,parent,false)
        return PokemonHolder(view,itemClick)
    }

    override fun onBindViewHolder(holder: PokemonHolder, position: Int) {
        holder.bindPokemon(pokemons[position], context)
    }

    override fun getItemCount(): Int {
       return pokemons.count()
    }
    inner class PokemonHolder(itemView: View, val itemClick: (Pokemon.PokemonArray) -> Unit): RecyclerView.ViewHolder(itemView){
        val pokemonName = itemView.findViewById<TextView>(R.id.pokemonName)
        val pokemonThumbnail = itemView.findViewById<ImageView>(R.id.imageViewThumbnail)

        fun bindPokemon(pokemon:Pokemon.PokemonArray, context: Context){
            val resourceId = context.resources.getIdentifier(pokemon.url,"drawable", context.packageName)
            pokemonThumbnail.setImageResource(resourceId)
            pokemonName.text = pokemon.name
            itemView.setOnClickListener { itemClick(pokemon) }
        }
    }
}